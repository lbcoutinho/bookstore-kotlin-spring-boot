package com.lbcoutinho.bookstore.services.impl

import com.lbcoutinho.bookstore.domain.AuthorSummary
import com.lbcoutinho.bookstore.domain.BookSummary
import com.lbcoutinho.bookstore.repositories.AuthorRepository
import com.lbcoutinho.bookstore.repositories.BookRepository
import com.lbcoutinho.bookstore.services.BookService
import com.lbcoutinho.bookstore.toBookEntity
import com.lbcoutinho.bookstore.util.ISBN
import com.lbcoutinho.bookstore.util.aBookEntity
import com.lbcoutinho.bookstore.util.aBookSummary
import com.lbcoutinho.bookstore.util.anAuthorEntity
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import kotlin.test.Test

@SpringBootTest
@Transactional
class BookServiceImplTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository
) {

    @Test
    fun `Should throw IllegalStateException given author is not saved on database`() {
        // Given
        val authorId = 1L
        // When / Then
        assertThrows<IllegalStateException> { bookService.upsertBook(ISBN, aBookSummary(authorId)) }
    }

    @Test
    fun `Should save new book`() {
        // Given
        val savedAuthor = authorRepository.save(anAuthorEntity())
        val inputBook = aBookSummary(savedAuthor.id!!)

        // When
        val (createdBook, isCreated) = bookService.upsertBook(ISBN, inputBook)

        // Then
        assertThat(isCreated).isTrue()
        assertThat(createdBook).isEqualTo(inputBook.toBookEntity(savedAuthor))

        val queriedBook = bookRepository.findByIdOrNull(createdBook.isbn)
        assertThat(queriedBook).isEqualTo(createdBook)
    }

    @Test
    fun `Should update existing book`() {
        // Given
        val savedAuthor1 = authorRepository.save(anAuthorEntity())
        val savedAuthor2 = authorRepository.save(anAuthorEntity())
        val savedBook = bookRepository.save(aBookEntity(savedAuthor1.id!!))
        val inputBook = BookSummary(
            isbn = savedBook.isbn,
            title = "New title",
            description = "New desc",
            image = "new-image.png",
            author = AuthorSummary(savedAuthor2.id, null, null)
        )

        // When
        val (updatedBook, isCreated) = bookService.upsertBook(ISBN, inputBook)

        // Then
        assertThat(isCreated).isFalse()
        assertThat(updatedBook).isEqualTo(inputBook.toBookEntity(savedAuthor2))

        val queriedBook = bookRepository.findByIdOrNull(updatedBook.isbn)
        assertThat(queriedBook).isEqualTo(updatedBook)
    }
}