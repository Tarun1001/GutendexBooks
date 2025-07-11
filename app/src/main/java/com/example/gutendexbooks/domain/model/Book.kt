package com.example.gutendexbooks.domain.model

data class Book(
    val id: Int,
    val title: String,
    val authors: List<Author>,
    val subjects: List<String>,
    val bookshelves: List<String>,
    val languages: List<String>,
    val formats: Map<String, String>,
    val downloadCount: Int,
    val coverImageUrl: String?
)