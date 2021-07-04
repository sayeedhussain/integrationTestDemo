package com.example.integrationTestDemo

import org.springframework.stereotype.Component
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

@Component
class BookClient(bookConfig: BookConfig) {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(bookConfig.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private val bookService: BookService by lazy {
        retrofit.create(BookService::class.java)
    }

    fun getBooks(): List<Book> {
        val response = bookService.getBooks().execute()
        return when (response.code()) {
            200 -> Optional.ofNullable(response.body()).orElse(emptyList())
            404 -> {
                println("status: 404"); emptyList()
            }
            else -> emptyList()
        }
    }
}
