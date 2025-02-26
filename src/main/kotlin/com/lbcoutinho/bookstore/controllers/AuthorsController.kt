package com.lbcoutinho.bookstore.controllers

import com.lbcoutinho.bookstore.domain.dto.AuthorDto
import com.lbcoutinho.bookstore.domain.dto.AuthorUpdateRequestDto
import com.lbcoutinho.bookstore.services.AuthorService
import com.lbcoutinho.bookstore.toAuthorDto
import com.lbcoutinho.bookstore.toAuthorEntity
import com.lbcoutinho.bookstore.toAuthorUpdateRequest
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1/authors"])
class AuthorsController(private val authorService: AuthorService) {

    @PostMapping
    @ResponseStatus(CREATED)
    fun createAuthor(@RequestBody authorDto: AuthorDto): ResponseEntity<AuthorDto> {
        return try {
            ResponseEntity.status(CREATED).body(
                authorService.create(
                    authorDto.toAuthorEntity()
                ).toAuthorDto()
            )
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping
    fun getAllAuthors(): List<AuthorDto> {
        return authorService.getAll().map { it.toAuthorDto() }
    }

    @GetMapping("/{id}")
    fun getAuthor(@PathVariable("id") id: Long): ResponseEntity<AuthorDto> {
        return ResponseEntity.ofNullable(authorService.getById(id)?.toAuthorDto())
    }

    @PutMapping("/{id}")
    fun updateAuthor(
        @PathVariable("id") id: Long,
        @RequestBody authorDto: AuthorDto
    ): ResponseEntity<AuthorDto> {
        return try {
            ResponseEntity.ok(
                authorService.fullUpdate(
                    id, authorDto.toAuthorEntity()
                ).toAuthorDto()
            )
        } catch (e: IllegalStateException) {
            ResponseEntity.notFound().build()
        }
    }

    @PatchMapping("/{id}")
    fun partiallyUpdateAuthor(
        @PathVariable("id") id: Long,
        @RequestBody authorDto: AuthorUpdateRequestDto
    ): ResponseEntity<AuthorDto> {
        return try {
            ResponseEntity.ok(
                authorService.partialUpdate(
                    id, authorDto.toAuthorUpdateRequest()
                ).toAuthorDto()
            )
        } catch (e: IllegalStateException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteAuthor(@PathVariable("id") id: Long): ResponseEntity<Unit> {
        authorService.delete(id)
        return ResponseEntity.noContent().build()
    }
}