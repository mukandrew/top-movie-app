package com.topmovies.data.local

import com.topmovies.data.MovieDataSource
import com.topmovies.data.entity.MovieEntity
import com.topmovies.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieLocalDataSource(private val movieDao: MovieDao): MovieDataSource {
    suspend fun saveMovie(movieEntity: MovieEntity) =
        withContext(Dispatchers.IO) {
            movieDao.insert(movieEntity)
        }

    suspend fun removeAllMovies() =
        withContext(Dispatchers.IO) {
            movieDao.removeAll()
        }

    suspend fun saveListMovie(movieEntities: List<MovieEntity>) =
        withContext(Dispatchers.IO) {
            movieDao.insertList(movieEntities)
        }

    override suspend fun getTopRatedMovies(): Resource<List<MovieEntity>>? =
        withContext(Dispatchers.IO) {
            movieDao.list().let {
                if (it.isNotEmpty()) {
                    Resource.Success(it)
                } else {
                    null
                }
            }
        }

    override suspend fun getMovie(movieId: String): Resource<MovieEntity>? =
        withContext(Dispatchers.IO) {
            movieDao.find(movieId).let {
                Resource.Success(it)
            }
        }
}