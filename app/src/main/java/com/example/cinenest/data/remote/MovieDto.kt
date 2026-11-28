package com.example.cinenest.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
    val page: Int = 1,
    val results: List<MovieDto>,
    @SerialName("total_pages") val totalPages: Int = 1
)

@Serializable
data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String = "",
    @SerialName("vote_average") val voteAverage: Double = 0.0,
    @SerialName("release_date") val releaseDate: String = "",
    @SerialName("genre_ids") val genreIds: List<Int> = emptyList(),
    @SerialName("poster_path") val posterPath: String? = null
)
