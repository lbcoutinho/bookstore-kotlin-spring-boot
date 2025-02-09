package com.lbcoutinho.bookstore.util

import com.lbcoutinho.bookstore.domain.dto.AuthorDto

fun anAuthorDto(id: Long? = null) = AuthorDto(
    id = null,
    name = "John Doe",
    age = 30,
    description = "Some description",
    image = "author-image.jpg"
)