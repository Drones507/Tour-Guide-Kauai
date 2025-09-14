package com.example.mykauaivacationapp.ui

import RecommendationDetailScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mykauaivacationapp.ui.CategoriesScreen
import com.example.mykauaivacationapp.ui.RecommendationsScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mykauaivacationapp.R
import com.example.mykauaivacationapp.data.Datasource
import com.example.mykauaivacationapp.model.Category
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text


enum class AppScreen{
    Categories,
    Recommendations,
    RecommendationDetail
}

@Composable
fun CategoriesScreen(
    viewModel: AppViewModel = viewModel(), // default ViewModel instance
    modifier: Modifier = Modifier
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
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFE1BEE7)),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = stringResource(R.string.city),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = modifier
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
                CategoryList(
                    categoryList = uiState.categoryList,
                    onDeleteClick = { viewModel.deleteCategory(it) }
                )
            }
        }
    }
}


@Composable
fun CategoryList(
    categoryList: List<Category>,
    onDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(categoryList) { category ->
            CategoryCard(
                category = category,
                onDeleteClick = onDeleteClick,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Composable
fun CategoryCard(
    category: Category,
    onDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
            Button(onClick = { onDeleteClick(category.name) }) {
                Text("Delete")
            }
        }
    }
}

@Composable
fun CityApp() {
    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppScreen.Categories.name
    ) {
        composable(AppScreen.Categories.name) {
            CategoriesScreen(
                viewModel = viewModel,
                onCategoryClick = {
                    navController.navigate(AppScreen.Recommendations.name)
                }
            )
        }

        composable(AppScreen.Recommendations.name) {
            RecommendationsScreen(
                viewModel = viewModel,
                onRecommendationClick = { recommendationId ->
                    navController.navigate("recommendationDetail/$recommendationId")
                }
            )
        }

        composable(
            route = "recommendationDetail/{recommendationId}",
            arguments = listOf(navArgument("recommendationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val recommendationId = backStackEntry.arguments?.getString("recommendationId") ?: return@composable
            RecommendationDetailScreen(
                recommendationId = recommendationId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}


