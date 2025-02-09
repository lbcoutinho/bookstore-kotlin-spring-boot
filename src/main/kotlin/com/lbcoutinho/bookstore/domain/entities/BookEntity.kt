package com.lbcoutinho.bookstore.domain.entities

import jakarta.persistence.*
import jakarta.persistence.CascadeType.DETACH

@Entity
@Table(name = "books")
data class BookEntity(
    @Id
    val isbn: String,
    val title: String,
    val description: String,
    val image: String,
    @ManyToOne(cascade = [DETACH])
    @JoinColumn(name = "author_id")
    val author: AuthorEntity
)