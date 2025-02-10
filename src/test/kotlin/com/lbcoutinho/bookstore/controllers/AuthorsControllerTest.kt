package com.lbcoutinho.bookstore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lbcoutinho.bookstore.domain.entities.AuthorEntity
import com.lbcoutinho.bookstore.services.AuthorService
import com.lbcoutinho.bookstore.toAuthorEntity
import com.lbcoutinho.bookstore.util.anAuthorDto
import com.lbcoutinho.bookstore.util.anAuthorEntity
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

private const val AUTHORS_BASE_URL = "/v1/authors"

@SpringBootTest
@AutoConfigureMockMvc
class AuthorsControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    @MockkBean val authorService: AuthorService
) {

    private val objectMapper = ObjectMapper()

    @Test
    fun `Should return HTTP 201 with persisted entity given Author created successfully`() {
        // Given
        every { authorService.create(any()) }.answers { firstArg() }
        val expectedEntity = AuthorEntity(
            id = null,
            name = "John Doe",
            age = 30,
            description = "Some description",
            image = "author-image.jpg"
        )

        // When
        mockMvc.post(AUTHORS_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(anAuthorDto())
        }.andExpect {
            status { isCreated() }
        }

        // Then
        verify { authorService.create(expectedEntity) }
    }

    @Test
    fun `Should return HTTP 400 given trying to save author with id`() {
        // Given
        every { authorService.create(any()) }.throws(IllegalArgumentException())

        // When
        mockMvc.post(AUTHORS_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(anAuthorDto())
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `Should return HTTP 200 with empty list given database has NO authors saved`() {
        // Given
        every { authorService.getAll() }.returns(emptyList())

        // When
        mockMvc.get(AUTHORS_BASE_URL) {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { json("[]") }
        }

        // Then
        verify { authorService.getAll() }
    }

    @Test
    fun `Should return HTTP 200 with authors list given database has authors saved`() {
        // Given
        val authorsList = listOf(anAuthorEntity(1), anAuthorEntity(2))
        every { authorService.getAll() }.returns(authorsList)

        // When
        mockMvc.get(AUTHORS_BASE_URL) {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { json(objectMapper.writeValueAsString(authorsList)) }
        }

        // Then
        verify { authorService.getAll() }
    }

    @Test
    fun `Should return HTTP 404 given author NOT found on the database`() {
        // Given
        val id = 1L
        every { authorService.getById(id) }.returns(null)

        // When
        mockMvc.get("$AUTHORS_BASE_URL/$id") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
        }

        // Then
        verify { authorService.getById(id) }
    }

    @Test
    fun `Should return HTTP 200 with author given author was found on the database`() {
        // Given
        val id = 1L
        val expectedAuthor = anAuthorEntity(id)
        every { authorService.getById(id) }.returns(expectedAuthor)

        // When
        mockMvc.get("$AUTHORS_BASE_URL/$id") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { json(objectMapper.writeValueAsString(expectedAuthor)) }
        }

        // Then
        verify { authorService.getById(id) }
    }

    @Test
    fun `Should return HTTP 404 given trying to update author NOT found on the database`() {
        // Given
        val id = 1L
        every { authorService.fullUpdate(any(), any()) }.throws(IllegalStateException())

        // When
        mockMvc.put("$AUTHORS_BASE_URL/$id") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(anAuthorDto(id))
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `Should return HTTP 200 given full update is successful`() {
        // Given
        val id = 1L
        val inputAuthor = anAuthorDto(id)
        val expectedAuthor = anAuthorEntity(id)
        every { authorService.fullUpdate(id, inputAuthor.toAuthorEntity()) }.returns(expectedAuthor)

        // When
        mockMvc.put("$AUTHORS_BASE_URL/$id") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(anAuthorDto(id))
        }.andExpect {
            status { isOk() }
            content { json(objectMapper.writeValueAsString(expectedAuthor)) }
        }

        // Then
        verify { authorService.fullUpdate(id, inputAuthor.toAuthorEntity()) }
    }
}