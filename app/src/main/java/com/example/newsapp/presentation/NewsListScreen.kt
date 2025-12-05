package com.example.newsapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.components.FilterBottomSheet
import com.example.newsapp.presentation.components.NewsItem

private val categories = listOf("business", "entertainment", "general", "health")

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    viewModel: NewsListViewModel,
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(refreshing = state.isRefreshing, onRefresh = viewModel::refresh)
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (state.isFilterSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = viewModel::dismissFilterSheet,
            sheetState = sheetState
        ) {
            FilterBottomSheet(
                categories = categories,
                selectedCategory = state.selectedCategory,
                onCategorySelected = viewModel::selectCategory
            )
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Top Headlines") },
            actions = {
                IconButton(onClick = viewModel::openFilterSheet) {
                    Icon(imageVector = Icons.Outlined.FilterList, contentDescription = "Filter")
                }
            }
        )
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = viewModel::onSearchQueryChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text(text = "Search news") },
            singleLine = true,
            trailingIcon = {
                if (state.searchQuery.isNotBlank()) {
                    IconButton(onClick = viewModel::clearSearch) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear search")
                    }
                }
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.errorMessage != null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = state.errorMessage ?: "Unknown error", color = MaterialTheme.colorScheme.error)
                        TextButton(onClick = { viewModel.loadTopHeadlines(category = state.selectedCategory) }) {
                            Text(text = "Retry")
                        }
                    }
                }

                state.articles.isEmpty() && state.searchQuery.isNotBlank() -> {
                    Text(
                        text = "No Results Found",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.articles) { article ->
                            NewsItem(
                                article = article,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onArticleClick(article) }
                            )
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = state.isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
