package com.example.cinenest.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinenest.model.Movie
import com.example.cinenest.ui.components.SwipeableMovieCard
import com.example.cinenest.ui.theme.CineNestTheme

@Composable
fun MatchSwipeScreen(
    playerName: String,
    movie: Movie,
    position: Int,
    total: Int,
    onLike: () -> Unit,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = playerName,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$position / $total",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = {
                if (total > 0) {
                    (position.toFloat() / total)
                        .coerceIn(0f, 1f)
                } else {
                    0f
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            val cardWidth = minOf(
                maxWidth,
                (maxHeight - 120.dp)
                    .coerceAtLeast(0.dp) * (2f / 3f)
            )

            SwipeableMovieCard(
                movie = movie,
                onSwipeLeft = onSkip,
                onSwipeRight = onLike,
                modifier = Modifier.width(cardWidth)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledIconButton(
                onClick = onSkip,
                modifier = Modifier.size(64.dp),
                shape = CircleShape,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color(0xFFE50914),
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Skip",
                    modifier = Modifier.size(32.dp)
                )
            }

            FilledIconButton(
                onClick = onLike,
                modifier = Modifier.size(64.dp),
                shape = CircleShape,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Like",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 740
)
@Composable
fun MatchSwipeScreenPreview() {
    CineNestTheme {
        Surface {
            MatchSwipeScreen(
                playerName = "Player 1",
                movie = Movie(
                    id = 1,
                    title = "Interstellar",
                    description = "A journey through space.",
                    rating = 8.7,
                    year = 2014,
                    genre = "Sci-Fi"
                ),
                position = 3,
                total = 10,
                onLike = {},
                onSkip = {}
            )
        }
    }
}