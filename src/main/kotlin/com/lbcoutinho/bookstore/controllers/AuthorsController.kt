package com.lbcoutinho.bookstore.controllers

import com.lbcoutinho.bookstore.domain.dto.AuthorDto
import com.lbcoutinho.bookstore.services.AuthorService
import com.lbcoutinho.bookstore.toAuthorDto
import com.lbcoutinho.bookstore.toAuthorEntity
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/v1/authors"])
class AuthorsController(private val authorService: AuthorService) {

    @PostMapping
    @ResponseStatus(CREATED)
    fun createAuthor(@RequestBody author: AuthorDto): AuthorDto {
        return authorService.save(
            author.toAuthorEntity()
        ).toAuthorDto()
    }
}