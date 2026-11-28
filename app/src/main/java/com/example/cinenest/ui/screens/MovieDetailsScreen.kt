package com.example.cinenest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinenest.model.Movie
import com.example.cinenest.ui.components.CineNestTopBar
import com.example.cinenest.ui.components.MoviePoster
import com.example.cinenest.ui.components.RatingBadge

@Composable
fun MovieDetailsScreen(
    movie: Movie?,
    onFavoriteClick: () -> Unit,
    onWatchedClick: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CineNestTopBar(
                title = "Movie details",
                canNavigateBack = true,
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (movie == null) {
                Text(
                    text = "Movie not found",
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    MoviePoster(
                        posterUrl = movie.posterUrl,
                        contentDescription = movie.title,
                        modifier = Modifier
                            .width(120.dp)
                            .height(180.dp)
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = if (movie.year > 0) movie.year.toString() else "Year unknown",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = movie.genre,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        RatingBadge(rating = movie.rating)
                    }
                }
                Text(
                    text = movie.description,
                    style = MaterialTheme.typography.bodyLarge
                )
                Button(
                    onClick = onFavoriteClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (movie.isFavorite) "Remove from favorites" else "Add to favorites")
                }
                OutlinedButton(
                    onClick = onWatchedClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (movie.isWatched) "Watched ✓" else "Mark as watched")
                }
            }
        }
    }
}
