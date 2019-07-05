package com.topmovies.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val MOVIE_TABLE = "movies"

@Entity(tableName = MOVIE_TABLE)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val id: Int,
    val title: String,
    val backdropPath: String,
    val overview: String
) {
}