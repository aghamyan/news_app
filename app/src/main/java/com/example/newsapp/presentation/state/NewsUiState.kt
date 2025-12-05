package com.example.newsapp.presentation.state

import com.example.newsapp.domain.model.Article

data class NewsUiState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val selectedCategory: String? = null,
    val isFilterSheetOpen: Boolean = false
)
