package com.example.newsapp.data.repository

import com.example.newsapp.data.mapper.toDomain
import com.example.newsapp.data.remote.NewsApiService
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val apiService: NewsApiService,
    private val apiKey: String
) : NewsRepository {

    private var cachedArticles: List<Article> = emptyList()

    override suspend fun getTopHeadlines(category: String?, query: String?): Result<List<Article>> {
        return try {
            val response = apiService.getTopHeadlines(
                apiKey = apiKey,
                category = category,
                query = query
            )
            val articles = response.articles.orEmpty().map { it.toDomain().copy(category = category ?: it.category) }
            cachedArticles = articles
            Result.success(articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun searchCachedHeadlines(keyword: String): List<Article> {
        return cachedArticles.filter { article ->
            article.title.contains(keyword, ignoreCase = true) ||
                article.description.contains(keyword, ignoreCase = true)
        }
    }
}
