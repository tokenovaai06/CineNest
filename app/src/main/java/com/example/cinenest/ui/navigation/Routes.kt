package com.example.cinenest.ui.navigation

object Routes {
    const val MOVIE_LIST = "movie_list"
    const val MOVIE_DETAILS = "movie_details/{movieId}"
    const val ADD_MOVIE = "add_movie"

    fun movieDetails(movieId: Int): String = "movie_details/$movieId"
}