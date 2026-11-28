package com.example.cinenest.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinenest.model.Movie
import com.example.cinenest.ui.theme.CineNestTheme
import kotlinx.coroutines.launch
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlin.math.abs

@Composable
fun SwipeableMovieCard(
    movie: Movie,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    modifier: Modifier = Modifier
) {
    val offsetX = remember(movie.id) {
        Animatable(0f)
    }

    val scope = rememberCoroutineScope()
    val swipeLeft by rememberUpdatedState(onSwipeLeft)
    val swipeRight by rememberUpdatedState(onSwipeRight)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                translationX = offsetX.value
                rotationZ = offsetX.value / 40f
            }
            .pointerInput(movie.id) {
                val threshold = size.width * 0.3f
                val exitDistance = size.width * 2f
                var dragPosition = 0f
                var isAnimating = false

                detectDragGestures(
                    onDragStart = {
                        dragPosition = offsetX.value
                    },
                    onDragEnd = {
                        if (!isAnimating) {
                            isAnimating = true

                            scope.launch {
                                when {
                                    dragPosition > threshold -> {
                                        offsetX.animateTo(
                                            exitDistance,
                                            tween(250)
                                        )
                                        swipeRight()
                                    }

                                    dragPosition < -threshold -> {
                                        offsetX.animateTo(
                                            -exitDistance,
                                            tween(250)
                                        )
                                        swipeLeft()
                                    }

                                    else -> {
                                        offsetX.animateTo(
                                            0f,
                                            tween(250)
                                        )
                                        isAnimating = false
                                    }
                                }
                            }
                        }
                    },
                    onDragCancel = {
                        if (!isAnimating) {
                            scope.launch {
                                offsetX.animateTo(0f)
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()

                        if (!isAnimating) {
                            dragPosition += dragAmount.x
                            val target = dragPosition

                            scope.launch {
                                offsetX.snapTo(target)
                            }
                        }
                    }
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
            ) {
                MoviePoster(
                    posterUrl = movie.posterUrl,
                    contentDescription = movie.title,
                    modifier = Modifier.fillMaxSize()
                )

                val swipeOffset = offsetX.value
                val labelAlpha = (abs(swipeOffset) / 200f)
                    .coerceIn(0f, 1f)

                if (swipeOffset != 0f) {
                    val isLike = swipeOffset > 0f
                    val labelColor = if (isLike) {
                        Color(0xFF4CAF50)
                    } else {
                        Color(0xFFE50914)
                    }

                    Text(
                        text = if (isLike) "LIKE" else "NOPE",
                        color = labelColor,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 32.dp)
                            .graphicsLayer {
                                alpha = labelAlpha
                                rotationZ = if (isLike) -15f else 15f
                            }
                            .border(
                                width = 3.dp,
                                color = labelColor,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            )
                    )
                }
            }

            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "${movie.year} · ${movie.genre}",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            RatingBadge(rating = movie.rating)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeableMovieCardPreview() {
    CineNestTheme {
        SwipeableMovieCard(
            movie = Movie(
                id = 1,
                title = "Interstellar",
                description = "A journey through space.",
                rating = 8.7,
                year = 2014,
                genre = "Sci-Fi"
            ),
            onSwipeLeft = {},
            onSwipeRight = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}