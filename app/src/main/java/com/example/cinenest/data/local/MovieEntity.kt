package com.example.cinenest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val rating: Double,
    val year: Int,
    val genre: String,
    val posterUrl: String?,
    val isFavorite: Boolean,
    val isWatched: Boolean,
    val isCustom: Boolean,
    val position: Int
)
