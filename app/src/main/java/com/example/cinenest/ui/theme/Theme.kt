package com.example.cinenest.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CinemaColorScheme = darkColorScheme(
    primary = CinemaRed,
    primaryContainer = CinemaRed,
    onPrimaryContainer = CinemaWhite,
    surfaceContainer = CinemaSurface,
    surfaceContainerHigh = CinemaSurface,
    secondary = CinemaGold,
    tertiary = CinemaGray,
    background = CinemaBackground,
    surface = CinemaSurface,
    onPrimary = CinemaWhite,
    onSecondary = CinemaBackground,
    onTertiary = CinemaBackground,
    onBackground = CinemaWhite,
    onSurface = CinemaWhite,
    onSurfaceVariant = CinemaGray,
    surfaceVariant = CinemaSurface
)

@Composable
fun CineNestTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CinemaColorScheme,
        typography = Typography,
        content = content
    )
}