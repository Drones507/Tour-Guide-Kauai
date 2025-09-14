package com.example.mykauaivacationapp.model

data class Recommendation(
    val id: String,
    val categoryId: String,
    val placeName: String,
    val details: String? = null
)
