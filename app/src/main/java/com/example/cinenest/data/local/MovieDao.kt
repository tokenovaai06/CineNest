package com.example.cinenest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY isCustom DESC, position ASC, id ASC")
    fun observeAll(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<MovieEntity>

    @Upsert
    suspend fun upsertAll(movies: List<MovieEntity>)

    @Insert
    suspend fun insert(movie: MovieEntity)

    @Query("UPDATE movies SET isFavorite = NOT isFavorite WHERE id = :movieId")
    suspend fun toggleFavorite(movieId: Int)

    @Query("UPDATE movies SET isWatched = NOT isWatched WHERE id = :movieId")
    suspend fun toggleWatched(movieId: Int)

    @Query("SELECT MIN(id) FROM movies")
    suspend fun minId(): Int?

    @Query("DELETE FROM movies WHERE isCustom = 0 AND isFavorite = 0 AND isWatched = 0 AND id NOT IN (:keepIds)")
    suspend fun deleteUnusedTmdbMovies(keepIds: List<Int>)
}
