package com.topmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.topmovies.data.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}