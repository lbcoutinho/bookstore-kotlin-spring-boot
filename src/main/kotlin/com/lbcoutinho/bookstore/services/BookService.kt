package com.lbcoutinho.bookstore.services

import com.lbcoutinho.bookstore.domain.BookSummary
import com.lbcoutinho.bookstore.domain.entities.BookEntity

interface BookService {

    fun upsertBook(isbn: String, book: BookSummary): Pair<BookEntity, Boolean>

    fun getAllBooks(authorId: Long? = null): List<BookEntity>

    fun getBook(isbn: String): BookEntity?
}