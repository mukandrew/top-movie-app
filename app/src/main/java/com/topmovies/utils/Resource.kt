package com.topmovies.utils

sealed class Resource<out T : Any> {
    data class Success<T : Any>(val data: T) : Resource<T>()
    data class Error(val error: String) : Resource<Nothing>()
}