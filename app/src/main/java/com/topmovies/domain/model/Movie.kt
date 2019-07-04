package com.topmovies.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val backdropPath: String,
    val overview: String
){
}