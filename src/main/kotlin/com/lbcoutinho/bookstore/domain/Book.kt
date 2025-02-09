package com.lbcoutinho.bookstore.domain

import jakarta.persistence.*
import jakarta.persistence.CascadeType.DETACH

@Entity
@Table(name = "books")
data class Book(
    @Id
    val isbn: String,
    val title: String,
    val description: String,
    val image: String,
    @ManyToOne(cascade = [DETACH])
    @JoinColumn(name = "author_id")
    val author: Author
) {
}