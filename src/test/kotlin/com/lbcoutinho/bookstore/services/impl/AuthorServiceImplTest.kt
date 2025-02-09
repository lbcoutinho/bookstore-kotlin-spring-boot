package com.lbcoutinho.bookstore.services.impl

import com.lbcoutinho.bookstore.repositories.AuthorRepository
import com.lbcoutinho.bookstore.util.anAuthorEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class AuthorServiceImplTest @Autowired constructor(
    private val authorService: AuthorServiceImpl,
    private val authorRepository: AuthorRepository
) {

    @Test
    fun `Should save new author to the database`() {
        // Given
        val inputEntity = anAuthorEntity()

        // When
        val savedAuthor = authorService.save(inputEntity)

        // Then
        assertThat(savedAuthor.id).isNotNull()

        val queriedAuthor = authorRepository.findByIdOrNull(savedAuthor.id!!)
        assertThat(queriedAuthor).isNotNull()

        assertThat(queriedAuthor).isEqualTo(anAuthorEntity(savedAuthor.id))
    }
}