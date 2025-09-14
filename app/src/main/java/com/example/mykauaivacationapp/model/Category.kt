package com.example.mykauaivacationapp.model

data class Category(
    val name: String,
    val id: String = name.lowercase(),
    val recommendations: List<Recommendation> = emptyList()
)