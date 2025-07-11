package com.example.gutendexbooks.data.mapper

import com.example.gutendexbooks.data.dto.AuthorDto
import com.example.gutendexbooks.data.dto.BookDto
import com.example.gutendexbooks.domain.model.Author
import com.example.gutendexbooks.domain.model.Book

fun BookDto.toDomain(): Book {
    return Book(
        id = id,
        title = title,
        authors = authors.map { it.toDomain() },
        subjects = subjects,
        bookshelves = bookshelves,
        languages = languages,
        formats = formats,
        downloadCount = download_count,
        coverImageUrl = formats.entries.find {
            it.key.contains("image/", ignoreCase = true)
        }?.value
    )
}
fun AuthorDto.toDomain(): Author {
    return Author(
        name = name,
        birthYear = birth_year,
        deathYear = death_year
    )
}