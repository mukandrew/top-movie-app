package com.topmovies.data

import com.topmovies.data.local.MovieLocalDataSource
import com.topmovies.data.entity.MovieEntity
import com.topmovies.data.remote.MovieRemoteDataSource
import com.topmovies.utils.Resource

class MovieRepository(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
) {
    suspend fun getTopRatedMovie(): Resource<List<MovieEntity>> {
        val topRatedMovies = movieLocalDataSource.getTopRatedMovies()
        return if (topRatedMovies.isNotEmpty()) Resource.Success(topRatedMovies) else getMovieListRemote()
    }

    private suspend fun getMovieListRemote(): Resource<List<MovieEntity>> =
        when (val topMoviesResource = movieRemoteDataSource.getTopRatedMovies()) {
            is Resource.Success -> {
                movieLocalDataSource.saveListMovie(topMoviesResource.data)
                topMoviesResource
            }
            is Resource.Error -> topMoviesResource
        }

    suspend fun getMovie(movieId: String): Resource<MovieEntity> {
        val movie = movieLocalDataSource.getMovie(movieId)
        return if (movie != null) Resource.Success(movie) else getMovieRemote(movieId)
    }

    private suspend fun getMovieRemote(movieId: String): Resource<MovieEntity> {
        val movieResource = movieRemoteDataSource.getMovie(movieId)
        if (movieResource is Resource.Success) {
            movieLocalDataSource.saveMovie(movieResource.data)
        }
        return movieResource
    }
}