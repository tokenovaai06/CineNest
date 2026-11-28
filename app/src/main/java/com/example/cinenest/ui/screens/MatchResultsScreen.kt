package com.example.cinenest.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinenest.model.Movie
import com.example.cinenest.ui.components.MovieCard
import com.example.cinenest.ui.theme.CineNestTheme

@Composable
fun MatchResultsScreen(
    matches: List<Movie>,
    onMovieClick: (Int) -> Unit,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    var visible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        visible = true
    }

    val titleScale by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = "Match title animation"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (matches.isNotEmpty()) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "It's a match!",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.scale(titleScale)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "You both liked these movies!",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = matches,
                    key = { it.id }
                ) { movie ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onMovieClick(movie.id)
                        }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Text(
                                text = "${movie.year} · ${movie.genre}",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Text(
                                text = "★ ${movie.rating}",
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }

        } else {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No matches this time",
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Try again to find a movie you both like.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onPlayAgain,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Play again",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 740
)
@Composable
fun MatchResultsScreenPreview() {
    CineNestTheme {
        Surface {
            MatchResultsScreen(
                matches = listOf(
                    Movie(
                        id = 1,
                        title = "Interstellar",
                        description = "A journey through space.",
                        rating = 8.7,
                        year = 2014,
                        genre = "Sci-Fi"
                    ),
                    Movie(
                        id = 2,
                        title = "Avatar",
                        description = "A journey to Pandora.",
                        rating = 8.1,
                        year = 2009,
                        genre = "Adventure"
                    )
                ),
                onMovieClick = {},
                onPlayAgain = {}
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 740
)
@Composable
fun MatchResultsEmptyPreview() {
    CineNestTheme {
        Surface {
            MatchResultsScreen(
                matches = emptyList(),
                onMovieClick = {},
                onPlayAgain = {}
            )
        }
    }
}