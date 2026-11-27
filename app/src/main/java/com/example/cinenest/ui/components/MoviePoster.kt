package com.example.cinenest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.cinenest.ui.theme.CinemaSurface
import com.example.cinenest.ui.theme.CinemaGray

@Composable
fun MoviePoster(
    posterUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(CinemaSurface),
        contentAlignment = Alignment.Center
    ) {
        if (posterUrl.isBlank()) {
            Text(
                text = "No poster",
                color = CinemaGray
            )
        } else {
            AsyncImage(
                model = posterUrl,
                contentDescription = "Movie poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}