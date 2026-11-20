package com.example.cinenest.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinenest.model.Movie
import com.example.cinenest.ui.theme.CineNestTheme

@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${movie.year} • ${movie.genre}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Rating: ${movie.rating}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (movie.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (movie.isFavorite) "Remove from favorites" else "Add to favorites"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCardPreview() {
    CineNestTheme {
        MovieCard(
            movie = Movie(
                id = 1,
                title = "Interstellar",
                description = "A team of astronauts travels through space.",
                rating = 8.7,
                year = 2014,
                genre = "Sci-Fi"
            ),
            onClick = {},
            onFavoriteClick = {}
        )
    }
}