package com.example.newsapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FilterBottomSheet(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Categories",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = "All",
            style = MaterialTheme.typography.bodyLarge,
            color = if (selectedCategory == null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onCategorySelected(null) }
        )
        categories.forEach { category ->
            Text(
                text = category.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.bodyLarge,
                color = if (selectedCategory == category) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onCategorySelected(category) }
            )
        }
    }
}
