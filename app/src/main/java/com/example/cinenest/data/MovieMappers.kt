package com.example.cinenest.data

import com.example.cinenest.data.local.MovieEntity
import com.example.cinenest.data.remote.MovieDto
import com.example.cinenest.model.Movie
import kotlin.math.roundToInt

private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

private val tmdbGenres = mapOf(
    28 to "Action",
    12 to "Adventure",
    16 to "Animation",
    35 to "Comedy",
    80 to "Crime",
    99 to "Documentary",
    18 to "Drama",
    10751 to "Family",
    14 to "Fantasy",
    36 to "History",
    27 to "Horror",
    10402 to "Music",
    9648 to "Mystery",
    10749 to "Romance",
    878 to "Sci-Fi",
    10770 to "TV Movie",
    53 to "Thriller",
    10752 to "War",
    37 to "Western"
)

fun MovieDto.toEntity(position: Int, isFavorite: Boolean, isWatched: Boolean): MovieEntity =
    MovieEntity(
        id = id,
        title = title,
        description = overview.ifBlank { "No description yet." },
        rating = (voteAverage * 10).roundToInt() / 10.0,
        year = releaseDate.take(4).toIntOrNull() ?: 0,
        genre = genreIds.mapNotNull { tmdbGenres[it] }.take(3).joinToString(", ").ifEmpty { "Unknown" },
        posterUrl = posterPath?.let { POSTER_BASE_URL + it },
        isFavorite = isFavorite,
        isWatched = isWatched,
        isCustom = false,
        position = position
    )

fun MovieEntity.toMovie(): Movie =
    Movie(
        id = id,
        title = title,
        description = description,
        rating = rating,
        year = year,
        genre = genre,
        isFavorite = isFavorite,
        isWatched = isWatched,
        posterUrl = posterUrl.orEmpty()
    )
