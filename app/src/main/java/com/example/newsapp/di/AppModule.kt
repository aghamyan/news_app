package com.example.newsapp.di

import com.example.newsapp.data.remote.NewsApiService
import com.example.newsapp.data.repository.NewsRepositoryImpl
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsapp.domain.usecase.SearchHeadlinesUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object AppModule {
    private const val BASE_URL = "https://newsapi.org/v2/"
    const val API_KEY = "YOUR_API_KEY"

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    private val newsApi: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }

    private val newsRepository: NewsRepository by lazy {
        NewsRepositoryImpl(apiService = newsApi, apiKey = API_KEY)
    }

    fun provideGetTopHeadlinesUseCase(): GetTopHeadlinesUseCase =
        GetTopHeadlinesUseCase(repository = newsRepository)

    fun provideSearchHeadlinesUseCase(): SearchHeadlinesUseCase =
        SearchHeadlinesUseCase(repository = newsRepository)
}
