package com.example.cinenest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cinenest.model.Movie
import com.example.cinenest.ui.components.CineNestTopBar
import com.example.cinenest.ui.components.MovieCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    movies: List<Movie>,
    isLoading: Boolean,
    errorMessage: String?,
    isLoadingMore: Boolean,
    loadMoreFailed: Boolean,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onAddMovieClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    // True when one of the last 5 items is on the screen
    val isNearEnd by remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total = listState.layoutInfo.totalItemsCount
            total > 0 && lastVisible >= total - 5
        }
    }

    // Runs again when the list grows, so a short new page can trigger the next one
    LaunchedEffect(isNearEnd, movies.size) {
        if (isNearEnd && movies.isNotEmpty()) {
            onLoadMore()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CineNestTopBar(
                title = "CineNest",
                canNavigateBack = false,
                onNavigateBack = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddMovieClick) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add movie")
            }
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isLoading && movies.isNotEmpty(),
            onRefresh = onRefresh,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                movies.isEmpty() && isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                movies.isEmpty() && errorMessage != null -> {
                    ErrorMessage(
                        message = errorMessage,
                        onRetry = onRefresh,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 88.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (errorMessage != null) {
                            item {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.errorContainer
                                    )
                                ) {
                                    Text(
                                        text = errorMessage,
                                        color = MaterialTheme.colorScheme.onErrorContainer,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp)
                                    )
                                }
                            }
                        }
                        items(items = movies, key = { movie -> movie.id }) { movie ->
                            MovieCard(
                                movie = movie,
                                onClick = { onMovieClick(movie.id) },
                                onFavoriteClick = { onFavoriteClick(movie.id) }
                            )
                        }
                        item {
                            LoadMoreFooter(
                                isLoadingMore = isLoadingMore,
                                loadMoreFailed = loadMoreFailed,
                                onRetry = onLoadMore
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorMessage(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Button(onClick = onRetry, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Retry")
        }
    }
}

@Composable
private fun LoadMoreFooter(
    isLoadingMore: Boolean,
    loadMoreFailed: Boolean,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoadingMore -> CircularProgressIndicator()
            loadMoreFailed -> TextButton(onClick = onRetry) {
                Text(text = "Could not load more movies. Tap to retry")
            }
        }
    }
}
