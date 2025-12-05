package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository

class SearchHeadlinesUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(keyword: String, category: String?): Result<List<Article>> {
        val result = repository.getTopHeadlines(category = category, query = keyword)
        return result.fold(
            onSuccess = { Result.success(it) },
            onFailure = {
                Result.success(repository.searchCachedHeadlines(keyword))
            }
        )
    }
}
