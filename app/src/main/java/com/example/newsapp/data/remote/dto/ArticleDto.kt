package com.example.newsapp.data.remote.dto

data class ArticleDto(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val source: SourceDto?
)
