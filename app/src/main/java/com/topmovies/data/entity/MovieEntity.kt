package com.topmovies.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    @ColumnInfo(name = "overview")
    val overview: String
) {
}