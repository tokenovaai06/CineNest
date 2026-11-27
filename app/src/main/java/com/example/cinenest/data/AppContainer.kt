package com.example.cinenest.data

import android.content.Context
import androidx.room.Room
import com.example.cinenest.BuildConfig
import com.example.cinenest.data.local.CineNestDatabase
import com.example.cinenest.data.remote.TmdbApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"

class AppContainer(context: Context) {

    private val json = Json { ignoreUnknownKeys = true }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val apiKey = BuildConfig.TMDB_API_KEY
            val request = chain.request()
            val urlBuilder = request.url.newBuilder()
                .addQueryParameter("language", "en-US")
            val requestBuilder = request.newBuilder()
            // TMDB gives two kinds of keys: a short API key and a long read access token
            if (apiKey.length > 40) {
                requestBuilder.header("Authorization", "Bearer $apiKey")
            } else {
                urlBuilder.addQueryParameter("api_key", apiKey)
            }
            chain.proceed(requestBuilder.url(urlBuilder.build()).build())
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(TMDB_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val tmdbApi: TmdbApi = retrofit.create(TmdbApi::class.java)

    private val database: CineNestDatabase = Room.databaseBuilder(
        context,
        CineNestDatabase::class.java,
        "cinenest_database"
    ).build()

    val movieRepository = MovieRepository(
        api = tmdbApi,
        movieDao = database.movieDao()
    )
}
