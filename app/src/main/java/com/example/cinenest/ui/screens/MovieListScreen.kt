package com.example.cinenest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinenest.model.Movie
import com.example.cinenest.ui.components.CineNestTopBar
import com.example.cinenest.ui.components.MovieCard

@Composable
fun MovieListScreen(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onAddMovieClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CineNestTopBar(
                title = "CineNest",
                canNavigateBack = false,
                onNavigateBack = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddMovieClick) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add movie")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = movies, key = { movie -> movie.id }) { movie ->
                MovieCard(
                    movie = movie,
                    onClick = { onMovieClick(movie.id) },
                    onFavoriteClick = { onFavoriteClick(movie.id) }
                )
            }
        }
    }
}