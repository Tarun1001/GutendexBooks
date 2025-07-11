package com.example.gutendexbooks.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gutendexbooks.data.paging.BooksPagingSource
import com.example.gutendexbooks.domain.model.Book
import com.example.gutendexbooks.domain.BooksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val apiService: GutendexApiService
) : BooksRepository {

    override fun getBooks(
        topic: String?,
        search: String?
    ): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {

                BooksPagingSource(apiService, topic, search)
            }
        ).flow
    }
}
