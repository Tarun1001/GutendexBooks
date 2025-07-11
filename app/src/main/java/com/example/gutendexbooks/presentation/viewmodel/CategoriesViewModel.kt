package com.example.gutendexbooks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gutendexbooks.domain.usecases.GetBookCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetBookCategoriesUseCase
) : ViewModel() {

    val categories = getCategoriesUseCase()
}