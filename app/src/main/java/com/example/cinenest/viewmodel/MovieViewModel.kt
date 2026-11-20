package com.example.cinenest.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cinenest.data.MovieRepository
import com.example.cinenest.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MovieUiState(
    val movies: List<Movie> = emptyList()
)

class MovieViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState(movies = MovieRepository.movies))
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    fun toggleFavorite(movieId: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                movies = currentState.movies.map { movie ->
                    if (movie.id == movieId) movie.copy(isFavorite = !movie.isFavorite) else movie
                }
            )
        }
    }

    fun toggleWatched(movieId: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                movies = currentState.movies.map { movie ->
                    if (movie.id == movieId) movie.copy(isWatched = !movie.isWatched) else movie
                }
            )
        }
    }

    fun addMovie(title: String, description: String, rating: Double, year: Int, genre: String) {
        _uiState.update { currentState ->
            val newId = (currentState.movies.maxOfOrNull { it.id } ?: 0) + 1
            val newMovie = Movie(
                id = newId,
                title = title,
                description = description,
                rating = rating,
                year = year,
                genre = genre
            )
            currentState.copy(movies = currentState.movies + newMovie)
        }
    }
}