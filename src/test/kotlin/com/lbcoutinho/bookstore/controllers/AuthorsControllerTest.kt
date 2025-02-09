package com.lbcoutinho.bookstore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lbcoutinho.bookstore.domain.dto.AuthorDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class AuthorsControllerTest @Autowired constructor(private val mockMvc: MockMvc) {

    private val objectMapper = ObjectMapper()

    @Test
    fun `should create Author then return HTTP 201 status on successful create`() {
        mockMvc.post("/v1/authors") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                AuthorDto(
                    id = null,
                    name = "John Doe",
                    age = 30,
                    description = "Good author",
                    image = "john.jpg"
                )
            )
        }.andExpect {
            status { isCreated() }
        }
    }
}