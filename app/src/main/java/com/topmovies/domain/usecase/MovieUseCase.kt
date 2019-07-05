package com.topmovies.domain.usecase

import com.topmovies.data.MovieRepository
import com.topmovies.data.entity.MovieEntity
import com.topmovies.domain.mapper.toMovieModel
import com.topmovies.domain.model.Movie
import com.topmovies.utils.Resource

class MovieUseCase(private val movieRepository: MovieRepository) {
    suspend fun getTopRatedMovie(): Resource<List<MovieEntity>> = movieRepository.getTopRatedMovie()

    suspend fun getMovie(movieId: String): Resource<MovieEntity> = movieRepository.getMovie(movieId)
}