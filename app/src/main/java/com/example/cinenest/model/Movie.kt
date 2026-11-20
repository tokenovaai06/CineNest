package com.example.cinenest.model

data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val rating: Double,
    val year: Int,
    val genre: String,
    val isFavorite: Boolean = false,
    val isWatched: Boolean = false
)
