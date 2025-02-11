package com.lbcoutinho.bookstore

import com.lbcoutinho.bookstore.domain.AuthorSummary
import com.lbcoutinho.bookstore.domain.AuthorUpdateRequest
import com.lbcoutinho.bookstore.domain.BookSummary
import com.lbcoutinho.bookstore.domain.dto.AuthorDto
import com.lbcoutinho.bookstore.domain.dto.AuthorSummaryDto
import com.lbcoutinho.bookstore.domain.dto.AuthorUpdateRequestDto
import com.lbcoutinho.bookstore.domain.dto.BookSummaryDto
import com.lbcoutinho.bookstore.domain.entities.AuthorEntity
import com.lbcoutinho.bookstore.domain.entities.BookEntity

fun AuthorEntity.toAuthorDto() = AuthorDto(
    id = this.id,
    name = this.name,
    age = this.age,
    description = this.description,
    image = this.image
)

fun AuthorDto.toAuthorEntity() = AuthorEntity(
    id = this.id,
    name = this.name,
    age = this.age,
    description = this.description,
    image = this.image
)

fun AuthorUpdateRequestDto.toAuthorUpdateRequest() = AuthorUpdateRequest(
    name = this.name,
    age = this.age,
    description = this.description,
    image = this.image
)

fun AuthorSummaryDto.toAuthorSummary() = AuthorSummary(
    id = this.id,
    name = this.name,
    image = this.image
)

fun BookSummaryDto.toBookSummary() = BookSummary(
    isbn = this.isbn,
    title = this.title,
    description = this.description,
    image = this.image,
    author = this.author.toAuthorSummary()
)

fun BookSummary.toBookEntity(author: AuthorEntity) = BookEntity(
    isbn = this.isbn,
    title = this.title,
    description = this.description,
    image = this.image,
    author = author
)

fun BookEntity.toBookSummaryDto() = BookSummaryDto(
    isbn = this.isbn,
    title = this.title,
    description = this.description,
    image = this.image,
    author = AuthorSummaryDto(this.author.id, this.author.name, this.author.image)
)