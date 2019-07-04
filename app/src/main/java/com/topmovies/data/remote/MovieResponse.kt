package com.topmovies.data.remote

import com.google.gson.annotations.SerializedName
import com.topmovies.data.entity.MovieEntity

class MovieResponse(
    var id: Int? = null,
    var title: String? = null,
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    var overview: String? = null
) {
    fun parseToEntity(): MovieEntity {
        return MovieEntity(0,id ?: 0, title ?: "", backdropPath ?: "", overview ?: "")
    }
}