package com.example.mykauaivacationapp.ui

import com.example.mykauaivacationapp.model.Category




data class AppUiState(
    val categoryList: List<Category> = emptyList(),
    val newCategoryInput: String = "",
    val isDuplicateCategory: Boolean = false,
    val selectedCategoryId: String? = null,
    val selectedRecommendationId: String? = null,
    val placeName: String = "",
    val details: String = ""
)
