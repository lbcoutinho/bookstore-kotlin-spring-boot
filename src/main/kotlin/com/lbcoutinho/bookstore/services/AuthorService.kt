package com.lbcoutinho.bookstore.services

import com.lbcoutinho.bookstore.domain.AuthorUpdateRequest
import com.lbcoutinho.bookstore.domain.entities.AuthorEntity

interface AuthorService {

    fun create(author: AuthorEntity): AuthorEntity

    fun getAll(): List<AuthorEntity>

    fun getById(id: Long): AuthorEntity?

    fun fullUpdate(id: Long, author: AuthorEntity): AuthorEntity

    fun partialUpdate(id: Long, authorUpdate: AuthorUpdateRequest): AuthorEntity

    fun delete(id: Long)
}