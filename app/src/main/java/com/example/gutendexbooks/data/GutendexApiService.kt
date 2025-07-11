package com.example.gutendexbooks.data

import com.example.gutendexbooks.data.dto.BooksResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GutendexApiService {
    @GET("books")
    suspend fun getBooks(
        @Query("page") page: Int = 1,
        @Query("topic") topic: String? = null,
        @Query("search") search: String? = null,
        @Query("mime_type") mimeType: String = "image/"
    ): BooksResponseDto
}