package com.lbcoutinho.bookstore.domain

data class AuthorUpdateRequest(
    val name: String? = null,
    val age: Int? = null,
    val description: String? = null,
    val image: String? = null
)