package com.example.newsapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsapp.domain.model.Article

@Composable
fun NewsItem(article: Article, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 2.dp
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = article.title,
                modifier = Modifier
                    .size(88.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = article.category,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                if (article.author.isNotBlank()) {
                    Text(
                        text = "By ${article.author}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
