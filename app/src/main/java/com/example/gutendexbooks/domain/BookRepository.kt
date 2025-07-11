package com.example.gutendexbooks.domain

import androidx.paging.PagingData
import com.example.gutendexbooks.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    fun getBooks(
        topic: String? = null,
        search: String? = null
    ): Flow<PagingData<Book>>
}