package com.topmovies.data.remote

import com.topmovies.data.MovieDataSource
import com.topmovies.data.entity.MovieEntity
import com.topmovies.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class MovieRemoteDataSource(private val retrofit: Retrofit): MovieDataSource {
    private val movieAPI = retrofit.create(MovieAPI::class.java)

    override suspend fun getTopRatedMovies(): Resource<List<MovieEntity>>? =
        withContext(Dispatchers.IO) {
            try {
                val response = movieAPI.getTopRatedMoviesAsync().await()
                if (response.isSuccessful) {
                    Resource.Success(response.body()?.parseToModelList()!!)
                } else {
                    Resource.Error("Vish, deu erro")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Error(e.localizedMessage ?: "")
            }

        }

    override suspend fun getMovie(movieId: String): Resource<MovieEntity>? =
            withContext(Dispatchers.IO) {
                val response = movieAPI.getMovieAsync(movieId).await()
                if (response.isSuccessful) {
                    Resource.Success(response.body()?.parseToEntity()!!)
                } else {
                    Resource.Error("Vish, deu erro")
                }
            }
}