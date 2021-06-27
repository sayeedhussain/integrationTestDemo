package com.example.integrationTestDemo

import retrofit2.Call
import retrofit2.http.GET

interface BookService {
    @GET("v3/909baf7d-0843-4ffb-83b8-f83a24e72310")
    fun getBooks(): Call<List<Book>?>
}