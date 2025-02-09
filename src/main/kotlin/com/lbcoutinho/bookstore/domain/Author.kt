package com.lbcoutinho.bookstore.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "authors")
data class Author(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long?,
    val name: String,
    val age: Int,
    val description: String,
    val image: String
) {
}