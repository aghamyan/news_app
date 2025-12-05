package com.example.newsapp.domain.repository

import com.example.newsapp.domain.model.Article

interface NewsRepository {
    suspend fun getTopHeadlines(category: String? = null, query: String? = null): Result<List<Article>>
    fun searchCachedHeadlines(keyword: String): List<Article>
}
