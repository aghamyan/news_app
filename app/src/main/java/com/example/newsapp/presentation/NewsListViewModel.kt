package com.example.newsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsapp.domain.usecase.SearchHeadlinesUseCase
import com.example.newsapp.presentation.state.NewsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val searchHeadlinesUseCase: SearchHeadlinesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState(isLoading = true))
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        loadTopHeadlines()
    }

    fun loadTopHeadlines(category: String? = _uiState.value.selectedCategory, query: String? = null) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = !state.isRefreshing,
                    errorMessage = null,
                    selectedCategory = category ?: state.selectedCategory
                )
            }
            val result = getTopHeadlinesUseCase(category = category, query = query)
            result.onSuccess { articles ->
                _uiState.update { state ->
                    state.copy(
                        articles = articles,
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = null
                    )
                }
            }.onFailure { throwable ->
                _uiState.update { state ->
                    state.copy(isLoading = false, isRefreshing = false, errorMessage = throwable.message)
                }
            }
        }
    }

    fun refresh() {
        _uiState.update { it.copy(isRefreshing = true) }
        val query = _uiState.value.searchQuery.takeIf { it.isNotBlank() }
        loadTopHeadlines(category = _uiState.value.selectedCategory, query = query)
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query, errorMessage = null) }
        if (query.isBlank()) {
            loadTopHeadlines(category = _uiState.value.selectedCategory)
        } else {
            performSearch(query)
        }
    }

    fun clearSearch() {
        _uiState.update { it.copy(searchQuery = "") }
        loadTopHeadlines(category = _uiState.value.selectedCategory)
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = searchHeadlinesUseCase(keyword = query, category = _uiState.value.selectedCategory)
            result.onSuccess { articles ->
                _uiState.update { state ->
                    state.copy(
                        articles = articles,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }.onFailure { throwable ->
                _uiState.update { state ->
                    state.copy(isLoading = false, errorMessage = throwable.message)
                }
            }
        }
    }

    fun openFilterSheet() {
        _uiState.update { it.copy(isFilterSheetOpen = true) }
    }

    fun dismissFilterSheet() {
        _uiState.update { it.copy(isFilterSheetOpen = false) }
    }

    fun selectCategory(category: String?) {
        _uiState.update { it.copy(selectedCategory = category, isFilterSheetOpen = false) }
        val query = _uiState.value.searchQuery.takeIf { it.isNotBlank() }
        if (query != null) {
            performSearch(query)
        } else {
            loadTopHeadlines(category = category)
        }
    }
}
