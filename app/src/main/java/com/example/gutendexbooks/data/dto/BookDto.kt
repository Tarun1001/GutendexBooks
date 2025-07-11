package com.example.gutendexbooks.data.dto

data class BookDto(
    val id: Int,
    val title: String,
    val authors: List<AuthorDto>,
    val subjects: List<String>,
    val bookshelves: List<String>,
    val languages: List<String>,
    val formats: Map<String, String>,
    val download_count: Int
)