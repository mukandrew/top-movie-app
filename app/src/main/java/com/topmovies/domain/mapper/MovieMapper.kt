package com.topmovies.domain.mapper

import com.topmovies.data.entity.MovieEntity
import com.topmovies.domain.model.Movie

fun MovieEntity.toMovieModel(): Movie = Movie(this.id, this.title, this.backdropPath, this.overview)