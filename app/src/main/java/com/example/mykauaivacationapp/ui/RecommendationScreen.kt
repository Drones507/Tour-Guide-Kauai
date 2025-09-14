package com.example.mykauaivacationapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mykauaivacationapp.model.Recommendation
import androidx.compose.foundation.clickable

@Composable
fun RecommendationsScreen(
    viewModel: AppViewModel,
    onRecommendationClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedCategory = uiState.categoryList.find { it.id == uiState.selectedCategoryId }
    var newPlace by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = selectedCategory?.name ?: "Unknown Category",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(selectedCategory?.recommendations ?: emptyList()) { rec ->
                RecommendationItem(
                    recommendation = rec,
                    onClick = { onRecommendationClick(rec.id) },
                    onDelete = { viewModel.deleteRecommendation(rec.categoryId, rec.id) }
                )
            }
        }

        if (errorText != null) {
            Text(text = errorText!!, color = MaterialTheme.colorScheme.error)
        }

        TextField(
            value = newPlace,
            onValueChange = {
                newPlace = it
                errorText = null
            },
            label = { Text("New recommendation") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val exists = selectedCategory?.recommendations?.any { it.placeName == newPlace } ?: false
                if (newPlace.isBlank()) {
                    errorText = "Place name cannot be empty."
                } else if (exists) {
                    errorText = "That place already exists."
                } else {
                    viewModel.addRecommendation(selectedCategory!!.id, newPlace)
                    newPlace = ""
                }
            },
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Save")
        }
    }
}

@Composable
fun RecommendationItem(
    recommendation: Recommendation,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = recommendation.placeName,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
                .clickable(onClick = onClick)
        )
        Button(onClick = onDelete, modifier = Modifier.padding(end = 8.dp)) {
            Text("X")
        }
    }
}
