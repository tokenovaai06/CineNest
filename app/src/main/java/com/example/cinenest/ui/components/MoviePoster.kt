package com.example.cinenest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import com.example.cinenest.ui.theme.CineNestTheme

@Composable
fun MoviePoster(
    posterUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    var hasError by remember(posterUrl) {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        if (posterUrl.isNullOrBlank() || hasError) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(48.dp)
            )
        } else {
            AsyncImage(
                model = posterUrl,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                onError = { hasError = true }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoviePosterPreview() {
    CineNestTheme {
        MoviePoster(
            posterUrl = null,
            contentDescription = "Movie poster",
            modifier = Modifier.size(
                width = 120.dp,
                height = 180.dp
            )
        )
    }
}