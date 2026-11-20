package com.example.cinenest.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MovieListScreen(
    onMovieClick: (Int) -> Unit,
    onAddMovieClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Movie list")
        Button(onClick = { onMovieClick(1) }) { Text("Open movie 1") }
        Button(onClick = { onMovieClick(2) }) { Text("Open movie 2") }
        Button(onClick = onAddMovieClick) { Text("Add movie") }
    }
}