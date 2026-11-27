package com.example.cinenest.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinenest.model.Movie
import com.example.cinenest.ui.theme.CineNestTheme
import androidx.compose.material3.CardDefaults
import com.example.cinenest.ui.theme.CinemaSurface

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
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = CinemaSurface
        )
    )  {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${movie.year} • ${movie.genre}",
                    style = MaterialTheme.typography.bodyMedium
                )
                RatingBadge(rating = movie.rating)
            }
            FavoriteButton(
                isFavorite = movie.isFavorite,
                onClick = onFavoriteClick
            )
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