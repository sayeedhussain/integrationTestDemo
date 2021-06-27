package com.example.integrationTestDemo

import org.springframework.stereotype.Component
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

@Component
class BookClient(bookConfig: BookConfig) {

    private val retrofit: Retrofit by lazy {
        println("<==========\nbaseUrl: ${bookConfig.baseUrl}\n=============>")
        Retrofit.Builder()
                .baseUrl(bookConfig.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private val bookService: BookService by lazy {
        retrofit.create(BookService::class.java)
    }

    fun getBooks(): List<Book> = Optional.ofNullable(bookService.getBooks().execute().body()).orElse(emptyList())
}
