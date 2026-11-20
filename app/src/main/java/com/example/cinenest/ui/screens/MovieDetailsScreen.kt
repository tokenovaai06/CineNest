package com.example.cinenest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinenest.model.Movie

@Composable
fun MovieDetailsScreen(
    movie: Movie?,
    onFavoriteClick: () -> Unit,
    onWatchedClick: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (movie == null) {
            Text(
                text = "Movie not found",
                style = MaterialTheme.typography.titleMedium
            )
        } else {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "${movie.year} • ${movie.genre}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Rating: ${movie.rating}",
                style = MaterialTheme.typography.titleMedium
            )
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
            Button(
                onClick = onWatchedClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (movie.isWatched) "Watched" else "Mark as watched")
            }
        }
        OutlinedButton(
            onClick = onNavigateBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Back")
        }
    }
}