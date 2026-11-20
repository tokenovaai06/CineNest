package com.example.cinenest.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cinenest.data.MovieRepository
import com.example.cinenest.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MovieUiState(
    val movies: List<Movie> = emptyList()
)

class MovieViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState(movies = MovieRepository.movies))
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()
}