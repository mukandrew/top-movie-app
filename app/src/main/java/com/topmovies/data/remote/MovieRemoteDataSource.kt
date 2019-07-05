package com.topmovies.data.remote

import com.topmovies.data.entity.MovieEntity
import com.topmovies.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class MovieRemoteDataSource(retrofit: Retrofit) {
    private val movieAPI = retrofit.create(MovieAPI::class.java)

    suspend fun getTopRatedMovies(): Resource<List<MovieEntity>> =
        withContext(Dispatchers.IO) {
            try {
                val response = movieAPI.getTopRatedMoviesAsync().await()
                if (response.isSuccessful) {
                    Resource.Success(response.body()?.parseToModelList()!!)
                } else {
                    Resource.Error("Connection Error")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Error(e.localizedMessage ?: "Internal Error")
            }

        }

    suspend fun getMovie(movieId: String): Resource<MovieEntity> =
        withContext(Dispatchers.IO) {
            try {
                val response = movieAPI.getMovieAsync(movieId).await()
                if (response.isSuccessful) {
                    Resource.Success(response.body()?.parseToEntity()!!)
                } else {
                    Resource.Error("Connection Error")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Error(e.localizedMessage ?: "Internal Error")
            }
        }
}