package com.topmovies.data

import com.topmovies.data.local.MovieLocalDataSource
import com.topmovies.data.entity.MovieEntity
import com.topmovies.data.remote.MovieRemoteDataSource
import com.topmovies.utils.Resource

class MovieRepository(
    val movieRemoteDataSource: MovieRemoteDataSource,
    val movieLocalDataSource: MovieLocalDataSource
) {
    suspend fun getTopRatedMovie(): Resource<List<MovieEntity>> {
        val localDataSource = movieLocalDataSource.getTopRatedMovies()
        return if (localDataSource is Resource.Success) {
            localDataSource
        } else {
            val remoteDataSource = movieRemoteDataSource.getTopRatedMovies()
            return if (remoteDataSource is Resource.Success) {
                movieLocalDataSource.saveListMovie(remoteDataSource.data)
                remoteDataSource
            } else {
                remoteDataSource!!
            }
        }

    }

    suspend fun getMovie(movieId: String): Resource<MovieEntity> {
        val localDataSource = movieLocalDataSource.getMovie(movieId)
        return if (localDataSource is Resource.Success) {
            localDataSource
        } else {
            val remoteDataSource = movieRemoteDataSource.getMovie(movieId)
            return if (remoteDataSource is Resource.Success) {
                movieLocalDataSource.saveMovie(remoteDataSource.data)
                remoteDataSource
            } else {
                remoteDataSource!!
            }
        }
    }
}