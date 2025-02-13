package com.lbcoutinho.bookstore.controllers

import com.lbcoutinho.bookstore.domain.dto.BookSummaryDto
import com.lbcoutinho.bookstore.services.BookService
import com.lbcoutinho.bookstore.toBookSummary
import com.lbcoutinho.bookstore.toBookSummaryDto
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/books")
class BooksController(private val bookService: BookService) {

    @PutMapping("/{isbn}")
    fun upsertBook(
        @PathVariable("isbn") isbn: String,
        @RequestBody book: BookSummaryDto
    ): ResponseEntity<BookSummaryDto> {
        try {
            val (savedBook, isCreated) = bookService.upsertBook(isbn, book.toBookSummary())
            val responseCode = if (isCreated) CREATED else OK;
            return ResponseEntity.status(responseCode).body(savedBook.toBookSummaryDto())
        } catch (e: IllegalStateException) {
            return ResponseEntity.badRequest().build()
        }
    }

    @GetMapping
    fun getAllBooks(@RequestParam("author") authorId: Long?): List<BookSummaryDto> {
        return bookService.getAllBooks(authorId).map { it.toBookSummaryDto() }
    }

    @GetMapping("/{isbn}")
    fun getBook(@PathVariable("isbn") isbn: String): ResponseEntity<BookSummaryDto> {
        return ResponseEntity.ofNullable(bookService.getBook(isbn)?.toBookSummaryDto())
    }
}