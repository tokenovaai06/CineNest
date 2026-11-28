package com.example.cinenest.data

import com.example.cinenest.data.local.MovieDao
import com.example.cinenest.data.local.MovieEntity
import com.example.cinenest.data.remote.TmdbApi
import com.example.cinenest.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// TMDB always returns 20 movies per page
private const val PAGE_SIZE = 20

class MovieRepository(
    private val api: TmdbApi,
    private val movieDao: MovieDao
) {

    val movies: Flow<List<Movie>> = movieDao.observeAll().map { entities ->
        entities.map { it.toMovie() }
    }

    // Loads page 1 again and removes old pages. Returns true if TMDB has more pages.
    suspend fun refreshPopularMovies(): Boolean = loadPage(page = 1, replaceOld = true)

    // Adds the next page to the end of the list. Returns true if TMDB has more pages.
    suspend fun loadPopularPage(page: Int): Boolean = loadPage(page = page, replaceOld = false)

    private suspend fun loadPage(page: Int, replaceOld: Boolean): Boolean {
        val response = api.getPopularMovies(page = page)
        val saved = movieDao.getAll().associateBy { it.id }
        val entities = response.results.mapIndexed { index, dto ->
            val old = saved[dto.id]
            dto.toEntity(
                position = (page - 1) * PAGE_SIZE + index,
                isFavorite = old?.isFavorite ?: false,
                isWatched = old?.isWatched ?: false
            )
        }
        if (replaceOld) {
            movieDao.deleteUnusedTmdbMovies(keepIds = entities.map { it.id })
        }
        movieDao.upsertAll(entities)
        return response.page < response.totalPages
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
