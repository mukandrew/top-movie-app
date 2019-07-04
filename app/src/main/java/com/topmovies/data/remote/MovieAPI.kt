package com.topmovies.data.remote

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieAPI {
    @GET("discover/movie?api_key=ae85d4b114abeaca9e4511b172c56173&language=pt-BR&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    fun getTopRatedMoviesAsync(): Deferred<Response<MovieListResponse>>

    @GET("movie/{movie_id}?api_key=ae85d4b114abeaca9e4511b172c56173")
    fun getMovieAsync(@Path("movie_id") movieId: String): Deferred<Response<MovieResponse>>
}