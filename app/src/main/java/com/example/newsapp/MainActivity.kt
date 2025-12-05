package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.di.AppModule
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.NewsDetailsScreen
import com.example.newsapp.presentation.NewsListScreen
import com.example.newsapp.presentation.NewsListViewModel
import com.example.newsapp.presentation.NewsViewModelFactory
import com.example.newsapp.presentation.navigation.Routes
import com.example.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                NewsNavHost()
            }
        }
    }

    @Composable
    private fun NewsNavHost() {
        val navController = rememberNavController()
        val viewModel: NewsListViewModel = viewModel(
            factory = NewsViewModelFactory(
                AppModule.provideGetTopHeadlinesUseCase(),
                AppModule.provideSearchHeadlinesUseCase()
            )
        )

        NavHost(navController = navController, startDestination = Routes.NEWS_LIST) {
            composable(Routes.NEWS_LIST) {
                NewsListScreen(
                    viewModel = viewModel,
                    onArticleClick = { article -> navigateToDetails(navController, article) }
                )
            }
            composable(Routes.DETAILS) {
                val article = navController.previousBackStackEntry?.savedStateHandle?.get<Article>("selectedArticle")
                article?.let {
                    NewsDetailsScreen(article = it) { navController.popBackStack() }
                }
            }
        }
    }

    private fun navigateToDetails(navController: NavHostController, article: Article) {
        navController.currentBackStackEntry?.savedStateHandle?.set("selectedArticle", article)
        navController.navigate(Routes.DETAILS)
    }
}
