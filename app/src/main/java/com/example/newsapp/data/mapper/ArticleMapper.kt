package com.example.newsapp.data.mapper

import com.example.newsapp.data.remote.dto.ArticleDto
import com.example.newsapp.domain.model.Article

fun ArticleDto.toDomain(): Article = Article(
    title = title.orEmpty(),
    author = author.orEmpty(),
    description = description.orEmpty(),
    url = url.orEmpty(),
    imageUrl = urlToImage.orEmpty(),
    publishedAt = publishedAt.orEmpty(),
    source = source?.name.orEmpty(),
    category = "General"
)
