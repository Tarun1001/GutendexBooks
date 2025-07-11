package com.example.gutendexbooks.data.dto

data class BooksResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<BookDto>
)