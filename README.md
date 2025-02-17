# Bookstore API using Kotlin and Spring Boot

Application created based on this quick course: https://www.youtube.com/watch?v=7iJ0NWRaWic  
Technologies:
 - Kotlin
 - Spring Boot 3
 - Spring Boot JPA
 - Postgres
 - Docker Compose

## APIs

The APIs provide CRUD operations to manipulate Authors and Books.  
Each Author has a relationship with Books of one to many.
When deleting an author the associated books are also deleted in cascade.

## Running

On the project root, run:
* `docker-compose up` to start the Postgres DB container.
* `./gradlew bootRun` to start the Spring Boot application.
* Access endpoints using `http://localhost:8080`

## Author API

### Create author
**Path:** `POST /v1/authors`  

**Request body:**
```
{
  "name": "string",
  "age": int,
  "description": "string",
  "image": "string"
}
```
**Response status:**
 - 201 CREATED if successful
 - 400 BAD REQUEST if invalid request body  

**Response body:**
```
{
  "id": int,
  "name": "string",
  "age": int,
  "description": "string",
  "image": "string"
}
```

### Get all authors
**Path:** `GET /v1/authors`  

**Response status:**
 - 200 OK for all cases 
**Response body:**
```
[
  {
    "id": int,
    "name": "string",
    "age": int,
    "description": "string",
    "image": "string"
  }
]
```

### Get author by id
**Path:** `GET /v1/authors/{id}`

**Response status:**
 - 200 OK if found author for given id
 - 404 NOT FOUND if author not found for given id

**Response body:**
```
{
  "id": int,
  "name": "string",
  "age": int,
  "description": "string",
  "image": "string"
}
```

### Full update author
**Path:** `PUT /v1/authors/{id}`

**Request body:**
```
{
  "name": "string",
  "age": int,
  "description": "string",
  "image": "string"
}
```
**Response status:**
 - 200 OK if update successful
 - 404 NOT FOUND if author not found for given id

**Response body:**
```
{
  "id": int,
  "name": "string",
  "age": int,
  "description": "string",
  "image": "string"
}
```

### Partial update author
**Path:** `PATCH /v1/authors/{id}`

**Request body:**
```
{
  "name": "string",
  "age": int,
  "description": "string",
  "image": "string"
}
```
**Response status:**
- 200 OK if update successful
- 404 NOT FOUND if author not found for given id

**Response body:**
```
{
  "id": int,
  "name": "string",
  "age": int,
  "description": "string",
  "image": "string"
}
```

### Delete author
**Path:** `DELETE /v1/authors/{id}`

**Response status:**
 - 204 NO CONTENT for all cases

## Books API

### Create book
**Path:** `PUT /v1/books/{isbn}`

**Request body:**
```
{
  "title": "string",
  "description": "string",
  "image": "string",
  "author": {
    "id": int
  }
}
```
**Response status:**
- 201 CREATED if book created successfully
- 200 OK if book already existed and was updated successfully
- 400 BAD REQUEST if invalid request body

**Response body:**
```
{
  "isbn": "string",
  "title": "string",
  "description": "string",
  "image": "string",
  "author": {
    "id": int,
    "name": "string",
    "image": "string"
  }
}
```

### Get all books
**Path:** `GET /v1/books`

**Query params:**
 - author={authorId} - Use it to get all books from given author

**Response status:**
 - 200 OK for all cases

**Response body:**
```
[
  {
    "isbn": "string",
    "title": "string",
    "description": "string",
    "image": "string",
    "author": {
      "id": int,
      "name": "string",
      "image": "string"
    }
  }
]
```

### Get book by ISBN
**Path:** `GET /v1/books/{isbn}`

**Response status:**
- 200 OK if found book for given id
- 404 NOT FOUND if book not found for given id

**Response body:**
```
{
  "isbn": "string",
  "title": "string",
  "description": "string",
  "image": "string",
  "author": {
    "id": int,
    "name": "string",
    "image": "string"
  }
}
```

### Full update book
Not implemented

### Partial update book
Not implemented

### Delete book
Not implemented