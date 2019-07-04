package com.topmovies.domain.usecase

import com.topmovies.data.MovieRepository
import com.topmovies.domain.mapper.toMovieModel
import com.topmovies.domain.model.Movie
import com.topmovies.utils.Resource

class MovieUseCase(val movieRepository: MovieRepository) {
    suspend fun getTopRatedMovie(): Resource<List<Movie>> =
        when (val resource = movieRepository.getTopRatedMovie()) {
            is Resource.Success -> {
                val movieList = resource.data.map { it.toMovieModel() }
                Resource.Success(movieList)
            }
            is Resource.Error -> resource
        }

    suspend fun getMovie(movieId: String): Resource<Movie> =
        when (val resource = movieRepository.getMovie(movieId)) {
            is Resource.Success -> Resource.Success(resource.data.toMovieModel())
            is Resource.Error -> resource

    }
}