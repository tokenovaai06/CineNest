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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import retrofit2.HttpException

data class MovieUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoadingMore: Boolean = false,
    val loadMoreFailed: Boolean = false
)

// Everything about loading, kept in one place so it is updated together
private data class LoadState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoadingMore: Boolean = false,
    val loadMoreFailed: Boolean = false,
    val canLoadMore: Boolean = true
)

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val loadState = MutableStateFlow(LoadState())
    private var nextPage = 2

    val uiState: StateFlow<MovieUiState> = combine(
        repository.movies,
        loadState
    ) { movies, state ->
        MovieUiState(
            movies = movies,
            isLoading = state.isLoading,
            errorMessage = state.errorMessage,
            isLoadingMore = state.isLoadingMore,
            loadMoreFailed = state.loadMoreFailed
        )
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
            loadState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val hasMorePages = repository.refreshPopularMovies()
                nextPage = 2
                loadState.update { it.copy(canLoadMore = hasMorePages, loadMoreFailed = false) }
            } catch (e: IOException) {
                loadState.update { it.copy(errorMessage = "No internet connection. Pull down to try again.") }
            } catch (e: HttpException) {
                val message = if (e.code() == 401) {
                    "TMDB API key is missing or invalid."
                } else {
                    "Server error (${e.code()}). Try again later."
                }
                loadState.update { it.copy(errorMessage = message) }
            } catch (e: SerializationException) {
                loadState.update { it.copy(errorMessage = "Unexpected data from the server.") }
            } finally {
                loadState.update { it.copy(isLoading = false) }
            }
        }
    }

    // Called when the user scrolls near the end of the list
    fun loadMore() {
        val state = loadState.value
        if (state.isLoading || state.isLoadingMore || !state.canLoadMore) return

        viewModelScope.launch {
            loadState.update { it.copy(isLoadingMore = true, loadMoreFailed = false) }
            try {
                val hasMorePages = repository.loadPopularPage(nextPage)
                nextPage++
                loadState.update { it.copy(canLoadMore = hasMorePages) }
            } catch (e: IOException) {
                loadState.update { it.copy(loadMoreFailed = true) }
            } catch (e: HttpException) {
                loadState.update { it.copy(loadMoreFailed = true) }
            } catch (e: SerializationException) {
                loadState.update { it.copy(loadMoreFailed = true) }
            } finally {
                loadState.update { it.copy(isLoadingMore = false) }
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
