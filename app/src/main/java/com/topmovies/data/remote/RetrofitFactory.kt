package com.topmovies.data.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {
    fun getRetrofit(): Retrofit {

        val okHttpClient = with(OkHttpClient.Builder()) {
            connectTimeout(1L, TimeUnit.MINUTES)
            readTimeout(30L, TimeUnit.SECONDS)
            writeTimeout(15L, TimeUnit.SECONDS)
            build()
        }

        return Retrofit
            .Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }
}