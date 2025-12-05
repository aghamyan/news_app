package com.example.newsapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsapp.domain.model.Article

@Composable
fun NewsDetailsScreen(article: Article, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
            Text(text = article.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            if (article.author.isNotBlank()) {
                Text(text = "By ${article.author}", style = MaterialTheme.typography.bodyMedium)
            }
            if (article.source.isNotBlank()) {
                Text(text = "Source: ${article.source}", style = MaterialTheme.typography.bodyMedium)
            }
            Text(text = article.description, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
