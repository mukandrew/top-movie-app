package com.topmovies.data.remote

import com.topmovies.data.entity.MovieEntity

data class MovieListResponse(
   var results: List<MovieResponse>? = null
) {
   fun parseToModelList(): List<MovieEntity> {
      val movies = mutableListOf<MovieEntity>()
      results?.forEach {
         movies.add(it.parseToEntity())
      }
      return movies
   }
}