package com.lbcoutinho.bookstore.services.impl

import com.lbcoutinho.bookstore.domain.BookSummary
import com.lbcoutinho.bookstore.domain.entities.BookEntity
import com.lbcoutinho.bookstore.repositories.AuthorRepository
import com.lbcoutinho.bookstore.repositories.BookRepository
import com.lbcoutinho.bookstore.services.BookService
import com.lbcoutinho.bookstore.toBookEntity
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository
) : BookService {

    @Transactional
    override fun upsertBook(isbn: String, book: BookSummary): Pair<BookEntity, Boolean> {
        val normalizedBook = book.copy(isbn = isbn)
        val isExists = bookRepository.existsById(isbn)

        val author = authorRepository.findByIdOrNull(book.author.id)
        checkNotNull(author)

        val savedBook = bookRepository.save(normalizedBook.toBookEntity(author))

        return Pair(savedBook, !isExists)
    }

    override fun getAllBooks(authorId: Long?): List<BookEntity> {
        return authorId?.let {
            bookRepository.findByAuthorId(it)
        } ?: bookRepository.findAll()
    }
}