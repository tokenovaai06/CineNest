package com.example.cinenest.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MovieListResponse
}
