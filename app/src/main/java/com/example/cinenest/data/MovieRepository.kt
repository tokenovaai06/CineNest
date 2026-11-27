package com.example.cinenest.data

import com.example.cinenest.data.local.MovieDao
import com.example.cinenest.data.local.MovieEntity
import com.example.cinenest.data.remote.TmdbApi
import com.example.cinenest.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val api: TmdbApi,
    private val movieDao: MovieDao
) {

    val movies: Flow<List<Movie>> = movieDao.observeAll().map { entities ->
        entities.map { it.toMovie() }
    }

    suspend fun refreshPopularMovies() {
        val response = api.getPopularMovies(page = 1)
        val saved = movieDao.getAll().associateBy { it.id }
        val entities = response.results.mapIndexed { index, dto ->
            val old = saved[dto.id]
            dto.toEntity(
                position = index,
                isFavorite = old?.isFavorite ?: false,
                isWatched = old?.isWatched ?: false
            )
        }
        movieDao.deleteUnusedTmdbMovies(keepIds = entities.map { it.id })
        movieDao.upsertAll(entities)
    }

    suspend fun toggleFavorite(movieId: Int) {
        movieDao.toggleFavorite(movieId)
    }

    suspend fun toggleWatched(movieId: Int) {
        movieDao.toggleWatched(movieId)
    }

    suspend fun addCustomMovie(title: String, description: String, rating: Double, year: Int, genre: String) {
        val newId = minOf(movieDao.minId() ?: 0, 0) - 1
        movieDao.insert(
            MovieEntity(
                id = newId,
                title = title,
                description = description,
                rating = rating,
                year = year,
                genre = genre,
                posterUrl = null,
                isFavorite = false,
                isWatched = false,
                isCustom = true,
                position = 0
            )
        )
    }
}
