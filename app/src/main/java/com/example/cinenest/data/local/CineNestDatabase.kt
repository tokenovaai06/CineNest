package com.example.cinenest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class CineNestDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}
