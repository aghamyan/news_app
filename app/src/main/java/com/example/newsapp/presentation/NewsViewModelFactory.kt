package com.example.newsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsapp.domain.usecase.SearchHeadlinesUseCase

class NewsViewModelFactory(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val searchHeadlinesUseCase: SearchHeadlinesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsListViewModel(getTopHeadlinesUseCase, searchHeadlinesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
