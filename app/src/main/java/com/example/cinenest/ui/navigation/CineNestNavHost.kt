package com.example.cinenest.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cinenest.ui.components.CineNestTopBar
import com.example.cinenest.ui.screens.MovieDetailsScreen
import com.example.cinenest.ui.screens.MovieListScreen
import com.example.cinenest.viewmodel.MovieViewModel

@Composable
fun CineNestNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: MovieViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.MOVIE_LIST,
        modifier = modifier
    ) {
        composable(route = Routes.MOVIE_LIST) {
            MovieListScreen(
                movies = uiState.movies,
                onMovieClick = { movieId ->
                    navController.navigate(Routes.movieDetails(movieId))
                },
                onFavoriteClick = { movieId -> viewModel.toggleFavorite(movieId) },
                onAddMovieClick = { navController.navigate(Routes.ADD_MOVIE) }
            )
        }

        composable(
            route = Routes.MOVIE_DETAILS,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            val movie = uiState.movies.find { it.id == movieId }
            MovieDetailsScreen(
                movie = movie,
                onFavoriteClick = { viewModel.toggleFavorite(movieId) },
                onWatchedClick = { viewModel.toggleWatched(movieId) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Routes.ADD_MOVIE) {
            Scaffold(
                topBar = {
                    CineNestTopBar(
                        title = "Add movie",
                        canNavigateBack = true,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            ) { innerPadding ->
                Text(
                    text = "Add movie screen",
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}