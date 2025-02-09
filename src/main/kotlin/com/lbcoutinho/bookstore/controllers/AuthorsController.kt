package com.lbcoutinho.bookstore.controllers

import com.lbcoutinho.bookstore.domain.dto.AuthorDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1/authors"])
class AuthorsController {

    @PostMapping
    fun createAuthor(@RequestBody author: AuthorDto): AuthorDto {
        // TODO impl
        return AuthorDto(1, "", 1, "", "");
    }
}