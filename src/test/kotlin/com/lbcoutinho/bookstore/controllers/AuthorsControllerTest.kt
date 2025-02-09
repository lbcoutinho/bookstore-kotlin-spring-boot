package com.lbcoutinho.bookstore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lbcoutinho.bookstore.domain.entities.AuthorEntity
import com.lbcoutinho.bookstore.services.AuthorService
import com.lbcoutinho.bookstore.util.anAuthorDto
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class AuthorsControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    @MockkBean val authorService: AuthorService
) {

    private val objectMapper = ObjectMapper()

    @Test
    fun `should create Author then return HTTP 201 status on successful create`() {
        // Given
        every { authorService.save(any()) }.answers { firstArg() }
        val expectedEntity = AuthorEntity(
            id = null,
            name = "John Doe",
            age = 30,
            description = "Some description",
            image = "author-image.jpg"
        )

        // When
        mockMvc.post("/v1/authors") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(anAuthorDto())
        }.andExpect {
            status { isCreated() }
        }

        // Then
        verify { authorService.save(expectedEntity) }
    }
}