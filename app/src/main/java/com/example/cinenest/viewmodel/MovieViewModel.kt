package com.example.cinenest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cinenest.CineNestApplication
import com.example.cinenest.data.MovieRepository
import com.example.cinenest.model.Movie
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import retrofit2.HttpException

data class MovieUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val errorMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<MovieUiState> = combine(
        repository.movies,
        isLoading,
        errorMessage
    ) { movies, loading, error ->
        MovieUiState(movies = movies, isLoading = loading, errorMessage = error)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MovieUiState(isLoading = true)
    )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                repository.refreshPopularMovies()
            } catch (e: IOException) {
                errorMessage.value = "No internet connection. Pull down to try again."
            } catch (e: HttpException) {
                errorMessage.value = if (e.code() == 401) {
                    "TMDB API key is missing or invalid."
                } else {
                    "Server error (${e.code()}). Try again later."
                }
            } catch (e: SerializationException) {
                errorMessage.value = "Unexpected data from the server."
            } finally {
                isLoading.value = false
            }
        }
    }

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch { repository.toggleFavorite(movieId) }
    }

    fun toggleWatched(movieId: Int) {
        viewModelScope.launch { repository.toggleWatched(movieId) }
    }

    fun addMovie(title: String, description: String, rating: Double, year: Int, genre: String) {
        viewModelScope.launch {
            repository.addCustomMovie(title, description, rating, year, genre)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as CineNestApplication
                MovieViewModel(application.container.movieRepository)
            }
        }
    }
}
