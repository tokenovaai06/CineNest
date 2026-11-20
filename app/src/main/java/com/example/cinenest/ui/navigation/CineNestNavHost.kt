package com.example.cinenest.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cinenest.ui.screens.MovieDetailsScreen
import com.example.cinenest.ui.screens.MovieListScreen

@Composable
fun CineNestNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.MOVIE_LIST,
        modifier = modifier
    ) {
        composable(route = Routes.MOVIE_LIST) {
            MovieListScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Routes.movieDetails(movieId))
                },
                onAddMovieClick = { navController.navigate(Routes.ADD_MOVIE) }
            )
        }

        composable(
            route = Routes.MOVIE_DETAILS,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            MovieDetailsScreen(
                movieId = movieId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Routes.ADD_MOVIE) {
            Text(text = "Add movie screen")
        }
    }
}