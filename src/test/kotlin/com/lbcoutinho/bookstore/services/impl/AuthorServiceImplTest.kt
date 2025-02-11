package com.lbcoutinho.bookstore.services.impl

import com.lbcoutinho.bookstore.domain.AuthorUpdateRequest
import com.lbcoutinho.bookstore.domain.entities.AuthorEntity
import com.lbcoutinho.bookstore.repositories.AuthorRepository
import com.lbcoutinho.bookstore.util.anAuthorEntity
import com.lbcoutinho.bookstore.util.anAuthorUpdateRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.util.stream.Stream

@SpringBootTest
class AuthorServiceImplTest @Autowired constructor(
    private val authorService: AuthorServiceImpl,
    private val authorRepository: AuthorRepository
) {

    @BeforeEach
    fun setup() {
        authorRepository.deleteAll()
    }

    @Test
    fun `Should save new author to the database`() {
        // Given
        val inputEntity = anAuthorEntity()

        // When
        val savedAuthor = authorService.create(inputEntity)

        // Then
        assertThat(savedAuthor.id).isNotNull()

        val queriedAuthor = authorRepository.findByIdOrNull(savedAuthor.id!!)
        assertThat(queriedAuthor).isNotNull()

        assertThat(queriedAuthor).isEqualTo(anAuthorEntity(savedAuthor.id))
    }

    @Test
    fun `Should throw IllegalArgumentException given trying to save author with id`() {
        // Given
        val inputEntity = anAuthorEntity(1)

        // When / Then
        assertThrows<IllegalArgumentException> { authorService.create(inputEntity) }
    }

    @Test
    fun `Should return empty list given there are NO authors saved on the database`() {
        // When
        val authorsList = authorService.getAll()

        // Then
        assertThat(authorsList).isEmpty()
    }

    @Test
    fun `Should return authors list given there are authors saved on the database`() {
        // Given
        val savedAuthor = authorRepository.save(anAuthorEntity())
        val expectedList = listOf(savedAuthor)

        // When
        val authorsList = authorService.getAll()

        // Then
        assertThat(authorsList).isEqualTo(expectedList)
    }

    @Test
    fun `Should return null given author NOT found on the database`() {
        // Given
        val id = 1L

        // When
        val author = authorService.getById(id)

        // Then
        assertThat(author).isNull()
    }

    @Test
    fun `Should return author entity given author found on the database`() {
        // Given
        val savedAuthor = authorRepository.save(anAuthorEntity())

        // When
        val author = authorService.getById(savedAuthor.id!!)

        // Then
        assertThat(author).isEqualTo(savedAuthor)
    }

    @Test
    fun `Should throw IllegalStateException given trying to update id not present on the database`() {
        // When / Then
        assertThrows<IllegalStateException> { authorService.fullUpdate(1, anAuthorEntity(1)) }
    }

    @Test
    fun `Should perform author full update`() {
        // Given
        val savedAuthor = authorRepository.save(anAuthorEntity())
        val updatedAuthor = AuthorEntity(savedAuthor.id, "New name", 99, "New desc", "New image")

        // When
        val author = authorService.fullUpdate(savedAuthor.id!!, updatedAuthor)

        // Then
        assertThat(author).isEqualTo(updatedAuthor)
        val retrievedAuthor = authorRepository.findByIdOrNull(savedAuthor.id!!)
        assertThat(retrievedAuthor).isEqualTo(updatedAuthor)
    }

    @Test
    fun `Should throw IllegalStateException given trying to partial update id not present on the database`() {
        // When / Then
        assertThrows<IllegalStateException> { authorService.partialUpdate(1, anAuthorUpdateRequest()) }
    }

    @ParameterizedTest
    @MethodSource("partialUpdateScenarios")
    fun `Should perform author partial update`(authorUpdateRequest: AuthorUpdateRequest,
                                               updatedAuthor: AuthorEntity) {
        // Given
        val savedAuthor = authorRepository.save(anAuthorEntity())

        // When
        val author = authorService.partialUpdate(savedAuthor.id!!, authorUpdateRequest)

        // Then
        assertThat(author).isEqualTo(updatedAuthor)
        val retrievedAuthor = authorRepository.findByIdOrNull(savedAuthor.id!!)
        assertThat(retrievedAuthor).isEqualTo(updatedAuthor)
    }

    companion object {
        @JvmStatic
        fun partialUpdateScenarios(): Stream<Arguments> {
            val authorEntity = anAuthorEntity()
            return Stream.of(
                arguments(
                    AuthorUpdateRequest(name = "New name"),
                    authorEntity.copy(id=1, name = "New name")
                ),
                arguments(
                    AuthorUpdateRequest(description = "New description"),
                    authorEntity.copy(id=2, description = "New description")
                ),
                arguments(
                    AuthorUpdateRequest(age = 99),
                    authorEntity.copy(id=3, age = 99)
                ),
                arguments(
                    AuthorUpdateRequest(image = "New image"),
                    authorEntity.copy(id=4, image = "New image")
                ),
                arguments(
                    AuthorUpdateRequest(),
                    authorEntity.copy(id=5)
                ),
            )
        }
    }
}