package com.example.cinenest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinenest.model.Movie
import com.example.cinenest.ui.components.MovieCard

@Composable
fun MovieListScreen(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onAddMovieClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "CineNest",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            items(items = movies, key = { movie -> movie.id }) { movie ->
                MovieCard(
                    movie = movie,
                    onClick = { onMovieClick(movie.id) },
                    onFavoriteClick = { onFavoriteClick(movie.id) }
                )
            }
        }
        FloatingActionButton(
            onClick = onAddMovieClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add movie")
        }
    }
}