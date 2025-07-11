package com.example.gutendexbooks.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gutendexbooks.data.GutendexApiService
import com.example.gutendexbooks.data.mapper.toDomain
import com.example.gutendexbooks.domain.model.Book
import javax.inject.Inject

class BooksPagingSource @Inject constructor(
    private val apiService: GutendexApiService,
    private val topic: String?,
    private val search: String?
) : PagingSource<Int, Book>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getBooks(
                page = page,
                topic = topic,
                search = search,
                mimeType = "image/"
            )

            val books = response.results.map { it.toDomain() }
            val nextPage = if (response.next != null) page + 1 else null
            val prevPage = if (response.previous != null) page - 1 else null

            LoadResult.Page(
                data = books,
                prevKey = prevPage,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}