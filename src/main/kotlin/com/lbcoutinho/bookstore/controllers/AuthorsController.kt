package com.lbcoutinho.bookstore.controllers

import com.lbcoutinho.bookstore.domain.dto.AuthorDto
import com.lbcoutinho.bookstore.services.AuthorService
import com.lbcoutinho.bookstore.toAuthorDto
import com.lbcoutinho.bookstore.toAuthorEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1/authors"])
class AuthorsController(private val authorService: AuthorService) {

    @PostMapping
    fun createAuthor(@RequestBody author: AuthorDto): AuthorDto {
        return authorService.save(
            author.toAuthorEntity()
        ).toAuthorDto()
    }
}