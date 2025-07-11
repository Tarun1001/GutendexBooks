package com.example.gutendexbooks.domain.usecases

import com.example.gutendexbooks.domain.model.BookCategory
import javax.inject.Inject

class GetBookCategoriesUseCase @Inject constructor() {
    operator fun invoke(): List<BookCategory> {
        return listOf(
            BookCategory("Fiction", "fiction"),
            BookCategory("History", "history"),
            BookCategory("Biography", "biography"),
            BookCategory("Science", "science"),
            BookCategory("Philosophy", "philosophy"),
            BookCategory("Poetry", "poetry"),
            BookCategory("Drama", "drama"),
            BookCategory("Adventure", "adventure"),
            BookCategory("Romance", "romance"),
            BookCategory("Mystery", "mystery")
        )
    }
}