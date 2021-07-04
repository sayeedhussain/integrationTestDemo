package com.example.integrationTestDemo

import org.springframework.stereotype.Component
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    fun getBooks(): Response<List<Book>?> {
        return bookService.getBooks().execute()
    }
}
