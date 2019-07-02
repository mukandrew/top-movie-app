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
        val remoteDataSource = movieRemoteDataSource.getTopRatedMovies()
        return if (remoteDataSource is Resource.Success) {
            movieLocalDataSource.saveListMovie(remoteDataSource.data)
            remoteDataSource
        } else {
            movieLocalDataSource.getTopRatedMovies() ?: remoteDataSource!!
        }
    }

    suspend fun getMovie(movieId: String): Resource<MovieEntity> {
        val remoteDataSource = movieRemoteDataSource.getMovie(movieId)
        return if (remoteDataSource is Resource.Success) {
            movieLocalDataSource.saveMovie(remoteDataSource.data)
            remoteDataSource
        } else {
            movieLocalDataSource.getMovie(movieId) ?: remoteDataSource!!
        }
    }
}