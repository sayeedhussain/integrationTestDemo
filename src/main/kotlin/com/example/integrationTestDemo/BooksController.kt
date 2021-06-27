package com.example.integrationTestDemo

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BooksController(val bookClient: BookClient) {

    @GetMapping("/books")
    fun getBooks(): ResponseEntity<List<Book>> {
        val books = bookClient.getBooks()
        return ResponseEntity(books, HttpStatus.OK)
    }
}