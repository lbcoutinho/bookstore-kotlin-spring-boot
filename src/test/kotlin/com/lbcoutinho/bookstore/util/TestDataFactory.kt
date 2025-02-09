package com.lbcoutinho.bookstore.util

import com.lbcoutinho.bookstore.domain.dto.AuthorDto
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