package com.example.mykauaivacationapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mykauaivacationapp.R
import com.example.mykauaivacationapp.model.Category

@Composable
fun CategoriesScreen(
    viewModel: AppViewModel,
    onCategoryClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(LocalLayoutDirection.current),
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(LocalLayoutDirection.current)
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE1BEE7)),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = stringResource(R.string.city),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextField(
                    value = uiState.newCategoryInput,
                    onValueChange = { viewModel.onCategoryInputChange(it) },
                    label = { Text("Add new category") },
                    modifier = Modifier.padding(8.dp)
                )

                Button(
                    onClick = { viewModel.onAddCategoryClick() },
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text("Add")
                }

                if (uiState.isDuplicateCategory) {
                    Text(
                        text = "Category already exists.",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }

                LazyColumn {
                    items(uiState.categoryList) { category ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    viewModel.selectCategory(category.id)
                                    onCategoryClick(category.id)
                                },
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = category.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.weight(1f)
                                )
                                Button(onClick = { viewModel.deleteCategory(category.name) }) {
                                    Text("Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
