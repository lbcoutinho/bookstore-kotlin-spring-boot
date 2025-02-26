package com.lbcoutinho.bookstore.services.impl

import com.lbcoutinho.bookstore.domain.AuthorUpdateRequest
import com.lbcoutinho.bookstore.domain.entities.AuthorEntity
import com.lbcoutinho.bookstore.repositories.AuthorRepository
import com.lbcoutinho.bookstore.services.AuthorService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl(private val authorRepository: AuthorRepository) : AuthorService {

    override fun create(author: AuthorEntity): AuthorEntity {
        require(author.id == null)
        return authorRepository.save(author)
    }

    override fun getAll(): List<AuthorEntity> {
        return authorRepository.findAll()
    }

    override fun getById(id: Long): AuthorEntity? {
        return authorRepository.findByIdOrNull(id)
    }

    @Transactional
    override fun fullUpdate(id: Long, author: AuthorEntity): AuthorEntity {
        check(authorRepository.existsById(id))
        val normalizedAuthor = author.copy(id = id)
        return authorRepository.save(normalizedAuthor)
    }

    @Transactional
    override fun partialUpdate(id: Long, authorUpdate: AuthorUpdateRequest): AuthorEntity {
        val existingAuthor = authorRepository.findByIdOrNull(id)
        checkNotNull(existingAuthor)

        val updatedAuthor = existingAuthor.copy(
            name = authorUpdate.name ?: existingAuthor.name,
            age = authorUpdate.age ?: existingAuthor.age,
            description = authorUpdate.description ?: existingAuthor.description,
            image = authorUpdate.image ?: existingAuthor.image,
        )

        return authorRepository.save(updatedAuthor)
    }

    override fun delete(id: Long) {
        authorRepository.deleteById(id)
    }
}