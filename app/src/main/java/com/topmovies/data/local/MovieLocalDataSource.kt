package com.topmovies.data.local

import com.topmovies.data.entity.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieLocalDataSource(private val movieDao: MovieDao) {
    suspend fun saveMovie(movieEntity: MovieEntity) =
        withContext(Dispatchers.IO) {
            movieDao.insert(movieEntity)
        }

    suspend fun saveListMovie(movieEntities: List<MovieEntity>) =
        withContext(Dispatchers.IO) {
            movieDao.insertList(movieEntities)
        }

    suspend fun getTopRatedMovies(): List<MovieEntity> =
        withContext(Dispatchers.IO) {
            movieDao.list()
        }

    suspend fun getMovie(movieId: String): MovieEntity =
        withContext(Dispatchers.IO) {
            movieDao.find(movieId)
        }
}