package com.example.gutendexbooks.domain.usecases

import androidx.paging.PagingData
import com.example.gutendexbooks.domain.BooksRepository
import com.example.gutendexbooks.domain.model.Book
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val repository: BooksRepository
) {
    operator fun invoke(
        topic: String? = null,
        search: String? = null
    ): Flow<PagingData<Book>> {
        return repository.getBooks(topic, search)
    }
}