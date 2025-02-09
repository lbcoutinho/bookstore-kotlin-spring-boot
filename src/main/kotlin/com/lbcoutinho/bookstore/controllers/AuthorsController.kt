package com.lbcoutinho.bookstore.controllers

import com.lbcoutinho.bookstore.domain.dto.AuthorDto
import com.lbcoutinho.bookstore.services.AuthorService
import com.lbcoutinho.bookstore.toAuthorDto
import com.lbcoutinho.bookstore.toAuthorEntity
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

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

    @GetMapping
    fun getAllAuthors(): List<AuthorDto> {
        return authorService.getAll().map { it.toAuthorDto() }
    }

    @GetMapping("/{id}")
    fun getAuthor(@PathVariable("id") id: Long): ResponseEntity<AuthorDto> {
        return ResponseEntity.ofNullable(authorService.getById(id)?.toAuthorDto())
    }
}