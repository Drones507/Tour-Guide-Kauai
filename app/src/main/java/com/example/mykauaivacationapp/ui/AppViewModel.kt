package com.example.mykauaivacationapp.ui

import androidx.lifecycle.ViewModel
import com.example.mykauaivacationapp.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.example.mykauaivacationapp.model.Recommendation

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState

    fun addCategory(name: String) {
        if (name.isBlank()) return
        if (_uiState.value.categoryList.any { it.name.equals(name, ignoreCase = true) }) return

        _uiState.update { current ->
            current.copy(categoryList = current.categoryList + Category(name))
        }
    }

    fun deleteCategory(name: String) {
        _uiState.update { current ->
            current.copy(categoryList = current.categoryList.filterNot { it.name == name })
        }
    }

    fun onCategoryInputChange(newInput: String) {
        _uiState.update { current ->
            current.copy(newCategoryInput = newInput, isDuplicateCategory = false)
        }
    }

    fun onAddCategoryClick() {
        val name = _uiState.value.newCategoryInput.trim()
        if (name.isBlank()) return

        if (_uiState.value.categoryList.any { it.name.equals(name, ignoreCase = true) }) {
            _uiState.update { current -> current.copy(isDuplicateCategory = true) }
            return
        }

        _uiState.update { current ->
            current.copy(
                categoryList = current.categoryList + Category(name),
                newCategoryInput = "",
                isDuplicateCategory = false
            )
        }
    }

    fun updateDetails(recId: String, details: String) {
        _uiState.update { state ->
            state.copy(
                categoryList = state.categoryList.map { cat ->
                    cat.copy(
                        recommendations = cat.recommendations.map {
                            if (it.id == recId) it.copy(details = details) else it
                        }
                    )
                }
            )
        }
    }

    fun selectCategory(categoryId: String) {
        _uiState.update { current ->
            current.copy(selectedCategoryId = categoryId)
        }
    }

    fun generateUniqueId(): String = java.util.UUID.randomUUID().toString()

    fun deleteRecommendation(categoryId: String, recId: String) {
        _uiState.update { current ->
            current.copy(
                categoryList = current.categoryList.map { category ->
                    if (category.id == categoryId) {
                        category.copy(
                            recommendations = category.recommendations.filterNot { it.id == recId }
                        )
                    } else category
                }
            )
        }
    }


    fun addRecommendation(categoryId: String, placeName: String) {
        _uiState.update { current ->
            current.copy(
                categoryList = current.categoryList.map { category ->
                    if (category.id == categoryId) {
                        val newRec = Recommendation(
                            id = generateUniqueId(), // generates a unique ID for the new recommendation
                            categoryId = categoryId,
                            placeName = placeName,
                            details = ""
                        )
                        category.copy(recommendations = category.recommendations + newRec)
                    } else category
                }
            )
        }
    }

    fun updateRecommendationDetails(recommendationId: String, newDetails: String) {
        val updatedList = _uiState.value.categoryList.map { category ->
            category.copy(recommendations = category.recommendations.map { rec ->
                rec.copy(details = rec.details + "\n• " + newDetails)
            })
        }
        _uiState.value = _uiState.value.copy(categoryList = updatedList)
    }

}