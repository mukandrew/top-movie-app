package com.topmovies.data.remote

import com.google.gson.annotations.SerializedName
import com.topmovies.data.entity.MovieEntity

class MovieResponse(
    var id: Int? = null,
    var title: String? = null,
    @SerializedName("poster_path")
    var posterPath: String? = null
) {
    fun parseToEntity(): MovieEntity {
        return MovieEntity(id ?: 0, title ?: "", posterPath ?: "")
    }
}