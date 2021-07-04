package com.example.integrationTestDemo

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class BooksController(val bookClient: BookClient) {

    @GetMapping("/books")
    fun getBooks(): ResponseEntity<List<Book>> {
        val response = bookClient.getBooks()
        println("status: ${response.code()}")

        return when (response.code()) {
            200 -> {
                val books = Optional.ofNullable(response.body()).orElse(emptyList())
                ResponseEntity(books, HttpStatus.OK)
            }
            401 -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            else -> ResponseEntity(emptyList(), HttpStatus.OK)
        }
    }
}