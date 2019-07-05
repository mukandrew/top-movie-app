package com.topmovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.topmovies.data.entity.MOVIE_TABLE
import com.topmovies.data.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM $MOVIE_TABLE")
    suspend fun list(): List<MovieEntity>

    @Query("SELECT * FROM $MOVIE_TABLE WHERE id = :movieId")
    suspend fun find(movieId: String): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(movieEntities: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    @Query("DELETE FROM $MOVIE_TABLE")
    suspend fun removeAll()
}