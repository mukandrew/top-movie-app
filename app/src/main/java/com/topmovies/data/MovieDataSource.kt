package com.topmovies.data

import com.topmovies.data.entity.MovieEntity
import com.topmovies.utils.Resource

interface MovieDataSource {
    suspend fun getTopRatedMovies(): Resource<List<MovieEntity>>?
    suspend fun getMovie(movieId: String): Resource<MovieEntity>?
}