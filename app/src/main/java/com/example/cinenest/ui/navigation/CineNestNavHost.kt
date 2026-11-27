package com.example.cinenest.ui.navigation

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
import com.example.cinenest.ui.screens.AddMovieScreen
import com.example.cinenest.ui.screens.MovieDetailsScreen
import com.example.cinenest.ui.screens.MovieListScreen
import com.example.cinenest.viewmodel.MovieViewModel

@Composable
fun CineNestNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    movieViewModel: MovieViewModel = viewModel(factory = MovieViewModel.Factory)
) {
    val uiState by movieViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.MOVIE_LIST,
        modifier = modifier
    ) {
        composable(route = Routes.MOVIE_LIST) {
            MovieListScreen(
                movies = uiState.movies,
                isLoading = uiState.isLoading,
                errorMessage = uiState.errorMessage,
                onRefresh = { movieViewModel.refresh() },
                onMovieClick = { movieId ->
                    navController.navigate(Routes.movieDetails(movieId))
                },
                onFavoriteClick = { movieId -> movieViewModel.toggleFavorite(movieId) },
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
                onFavoriteClick = { movieViewModel.toggleFavorite(movieId) },
                onWatchedClick = { movieViewModel.toggleWatched(movieId) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Routes.ADD_MOVIE) {
            AddMovieScreen(
                onSaveMovie = { title, description, rating, year, genre ->
                    movieViewModel.addMovie(title, description, rating, year, genre)
                    navController.popBackStack()
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
