package com.lbcoutinho.bookstore.domain.dto

data class AuthorUpdateRequestDto(
    val name: String?,
    val age: Int?,
    val description: String?,
    val image: String?
)