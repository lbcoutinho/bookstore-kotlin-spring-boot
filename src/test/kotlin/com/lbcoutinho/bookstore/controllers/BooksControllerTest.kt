package com.lbcoutinho.bookstore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lbcoutinho.bookstore.services.BookService
import com.lbcoutinho.bookstore.toBookSummary
import com.lbcoutinho.bookstore.toBookSummaryDto
import com.lbcoutinho.bookstore.util.aBookEntity
import com.lbcoutinho.bookstore.util.aBookSummaryDto
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
import org.springframework.test.web.servlet.put

private const val BOOKS_BASE_URL = "/v1/books"

@SpringBootTest
@AutoConfigureMockMvc
class BooksControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    @MockkBean val bookService: BookService
) {

    private val objectMapper = ObjectMapper()

    @Test
    fun `Should return HTTP 400 given service throws IllegalStateException`() {
        // Given
        every { bookService.upsertBook(any(), any()) }.throws(IllegalStateException())
        val isbn = "123"
        val inputBook = aBookSummaryDto(1);

        // When
        mockMvc.put("$BOOKS_BASE_URL/$isbn") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(inputBook)
        }.andExpect {
            status { isBadRequest() }
        }

        // Then
        verify { bookService.upsertBook(isbn, inputBook.toBookSummary()) }
    }

    @Test
    fun `Should return HTTP 201 with book summary given book is created successfully`() {
        // Given
        val isCreated = true
        val isbn = "123"
        val authorId = 1L
        val inputBook = aBookSummaryDto(authorId)
        val createdBook = aBookEntity(authorId)
        every { bookService.upsertBook(any(), any()) }.returns(Pair(createdBook, isCreated))

        // When
        mockMvc.put("$BOOKS_BASE_URL/$isbn") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(inputBook)
        }.andExpect {
            status { isCreated() }
            content { json(objectMapper.writeValueAsString(createdBook.toBookSummaryDto())) }
        }

        // Then
        verify { bookService.upsertBook(isbn, inputBook.toBookSummary()) }
    }

    @Test
    fun `Should return HTTP 200 with book summary given book is updated successfully`() {
        // Given
        val isCreated = false
        val isbn = "123"
        val authorId = 1L
        val inputBook = aBookSummaryDto(authorId)
        val updatedBook = aBookEntity(authorId)
        every { bookService.upsertBook(any(), any()) }.returns(Pair(updatedBook, isCreated))

        // When
        mockMvc.put("$BOOKS_BASE_URL/$isbn") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(inputBook)
        }.andExpect {
            status { isOk() }
            content { json(objectMapper.writeValueAsString(updatedBook.toBookSummaryDto())) }
        }

        // Then
        verify { bookService.upsertBook(isbn, inputBook.toBookSummary()) }
    }

    @Test
    fun `Should return HTTP 200 with all books list`() {
        // Given
        val returnedBooks = listOf(aBookEntity(1), aBookEntity(2))
        val expectedBooks = returnedBooks.map { it.toBookSummaryDto() }
        every { bookService.getAllBooks(null) }.returns(returnedBooks)

        // When
        mockMvc.get(BOOKS_BASE_URL) {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { json(objectMapper.writeValueAsString(expectedBooks)) }
        }

        // Then
        verify { bookService.getAllBooks(null) }
    }

    @Test
    fun `Should return HTTP 200 with author's books list`() {
        // Given
        val authorId = 1L
        val returnedBooks = listOf(aBookEntity(authorId), aBookEntity(authorId))
        val expectedBooks = returnedBooks.map { it.toBookSummaryDto() }
        every { bookService.getAllBooks(authorId) }.returns(returnedBooks)

        // When
        mockMvc.get("$BOOKS_BASE_URL?author=$authorId") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { json(objectMapper.writeValueAsString(expectedBooks)) }
        }

        // Then
        verify { bookService.getAllBooks(authorId) }
    }
}