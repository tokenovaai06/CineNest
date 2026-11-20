
package com.example.cinenest.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cinenest.ui.components.CineNestTopBar

@Composable
fun AddMovieScreen(
    onSaveMovie: (String, String, Double, Int, String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CineNestTopBar(
                title = "Add movie",
                canNavigateBack = true,
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(text = "Add a new movie")
        }
    }
}
