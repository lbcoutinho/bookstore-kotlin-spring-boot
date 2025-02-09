package com.lbcoutinho.bookstore.services

import com.lbcoutinho.bookstore.domain.entities.AuthorEntity

interface AuthorService {

    fun save(author: AuthorEntity): AuthorEntity

    fun getAll(): List<AuthorEntity>
}