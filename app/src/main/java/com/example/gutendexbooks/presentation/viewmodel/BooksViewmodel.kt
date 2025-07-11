package com.example.gutendexbooks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gutendexbooks.domain.model.Book
import com.example.gutendexbooks.domain.model.BookCategory
import com.example.gutendexbooks.domain.usecases.GetBookFormatsUseCase
import com.example.gutendexbooks.domain.usecases.GetBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val getBookFormatsUseCase: GetBookFormatsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BooksUiState())
    val uiState: StateFlow<BooksUiState> = _uiState.asStateFlow()

    val books: Flow<PagingData<Book>> = uiState.flatMapLatest { state ->
        getBooksUseCase(
            topic = state.selectedTopic,
            search = state.searchQuery.ifBlank { null }
        )
    }.cachedIn(viewModelScope)

    fun setCategory(category: BookCategory) {
        _uiState.value = _uiState.value.copy(
            selectedCategory = category,
            selectedTopic = category.topic
        )
    }

    fun setSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun getViewableBookUrl(book: Book): String? {
        return getBookFormatsUseCase(book.formats)
    }
}

data class BooksUiState(
    val selectedCategory: BookCategory? = null,
    val selectedTopic: String? = null,
    val searchQuery: String = ""
)