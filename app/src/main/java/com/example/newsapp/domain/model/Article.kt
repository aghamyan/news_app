package com.example.newsapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String,
    val author: String,
    val description: String,
    val url: String,
    val imageUrl: String,
    val publishedAt: String,
    val source: String,
    val category: String
) : Parcelable
