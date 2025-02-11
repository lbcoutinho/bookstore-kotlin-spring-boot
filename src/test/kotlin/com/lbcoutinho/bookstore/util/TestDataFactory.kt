package com.lbcoutinho.bookstore.util

import com.lbcoutinho.bookstore.domain.AuthorSummary
import com.lbcoutinho.bookstore.domain.AuthorUpdateRequest
import com.lbcoutinho.bookstore.domain.BookSummary
import com.lbcoutinho.bookstore.domain.dto.AuthorDto
import com.lbcoutinho.bookstore.domain.dto.AuthorSummaryDto
import com.lbcoutinho.bookstore.domain.dto.AuthorUpdateRequestDto
import com.lbcoutinho.bookstore.domain.dto.BookSummaryDto
import com.lbcoutinho.bookstore.domain.entities.AuthorEntity
import com.lbcoutinho.bookstore.domain.entities.BookEntity

const val ISBN = "978-3-16-148410-0"

fun anAuthorDto(id: Long? = null) = AuthorDto(
    id = id,
    name = "John Doe",
    age = 30,
    description = "Some description",
    image = "author-image.jpg"
)

fun anAuthorEntity(id: Long? = null) = AuthorEntity(
    id = id,
    name = "John Doe",
    age = 30,
    description = "Some description",
    image = "author-image.jpg"
)

fun anAuthorUpdateRequestDto() = AuthorUpdateRequestDto(
    name = "John Doe",
    age = 30,
    description = "Some description",
    image = "author-image.jpg"
)

fun anAuthorUpdateRequest() = AuthorUpdateRequest(
    name = "John Doe",
    age = 30,
    description = "Some description",
    image = "author-image.jpg"
)

fun aBookSummaryDto(authorId: Long) = BookSummaryDto(
    isbn = "123",
    title = "Book title",
    description = "Book desc",
    image = "book-image.jpg",
    author = AuthorSummaryDto(authorId, null, null)
)

fun aBookSummary(authorId: Long) = BookSummary(
    isbn = "123",
    title = "Book title",
    description = "Book desc",
    image = "book-image.jpg",
    author = AuthorSummary(authorId, null, null)
)

fun aBookEntity(authorId: Long) = BookEntity(
    isbn = "123",
    title = "Book title",
    description = "Book desc",
    image = "book-image.jpg",
    author = anAuthorEntity(authorId)
)