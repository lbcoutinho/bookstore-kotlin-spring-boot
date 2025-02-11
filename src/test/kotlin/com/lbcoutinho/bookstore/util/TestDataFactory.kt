package com.lbcoutinho.bookstore.util

import com.lbcoutinho.bookstore.domain.AuthorUpdateRequest
import com.lbcoutinho.bookstore.domain.dto.AuthorDto
import com.lbcoutinho.bookstore.domain.dto.AuthorUpdateRequestDto
import com.lbcoutinho.bookstore.domain.entities.AuthorEntity

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
