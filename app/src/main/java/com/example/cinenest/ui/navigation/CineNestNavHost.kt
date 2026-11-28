package com.example.cinenest.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cinenest.ui.components.CineNestBottomBar
import com.example.cinenest.ui.screens.AddMovieScreen
import com.example.cinenest.ui.screens.FavoritesScreen
import com.example.cinenest.ui.screens.MatchHandoffScreen
import com.example.cinenest.ui.screens.MatchIntroScreen
import com.example.cinenest.ui.screens.MatchResultsScreen
import com.example.cinenest.ui.screens.MatchSwipeScreen
import com.example.cinenest.ui.screens.MovieDetailsScreen
import com.example.cinenest.ui.screens.MovieListScreen
import com.example.cinenest.viewmodel.MatchPhase
import com.example.cinenest.viewmodel.MatchViewModel
import com.example.cinenest.viewmodel.MovieViewModel

private val tabRoutes = setOf(Routes.MOVIE_LIST, Routes.MATCH, Routes.FAVORITES)

@Composable
fun CineNestNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    movieViewModel: MovieViewModel = viewModel(factory = MovieViewModel.Factory),
    matchViewModel: MatchViewModel = viewModel()
) {
    val uiState by movieViewModel.uiState.collectAsState()
    val matchState by matchViewModel.uiState.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (currentRoute in tabRoutes) {
                CineNestBottomBar(
                    currentRoute = currentRoute,
                    onTabSelected = { tab ->
                        navController.navigate(tab.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.MOVIE_LIST,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
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
            ) { entry ->
                val movieId = entry.arguments?.getInt("movieId") ?: 0
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

            composable(route = Routes.MATCH) {
                // Match screens have no top bar, so they need room for the status bar
                val matchModifier = Modifier.statusBarsPadding()
                when (matchState.phase) {
                    MatchPhase.INTRO -> MatchIntroScreen(
                        onStart = { matchViewModel.start(uiState.movies) },
                        modifier = matchModifier
                    )
                    MatchPhase.PLAYER_ONE, MatchPhase.PLAYER_TWO -> {
                        val movie = matchState.currentMovie
                        if (movie != null) {
                            MatchSwipeScreen(
                                playerName = matchState.currentPlayerName,
                                movie = movie,
                                position = matchState.currentIndex + 1,
                                total = matchState.deck.size,
                                onLike = { matchViewModel.like() },
                                onSkip = { matchViewModel.skip() },
                                modifier = matchModifier
                            )
                        }
                    }
                    MatchPhase.HANDOFF -> MatchHandoffScreen(
                        nextPlayerName = "Player 2",
                        onReady = { matchViewModel.playerReady() },
                        modifier = matchModifier
                    )
                    MatchPhase.RESULTS -> MatchResultsScreen(
                        matches = matchState.matches,
                        onMovieClick = { movieId ->
                            navController.navigate(Routes.movieDetails(movieId))
                        },
                        onPlayAgain = { matchViewModel.playAgain() },
                        modifier = matchModifier
                    )
                }
            }

            composable(route = Routes.FAVORITES) {
                FavoritesScreen(
                    movies = uiState.movies.filter { it.isFavorite },
                    onMovieClick = { movieId ->
                        navController.navigate(Routes.movieDetails(movieId))
                    },
                    onFavoriteClick = { movieId -> movieViewModel.toggleFavorite(movieId) }
                )
            }
        }
    }
}
