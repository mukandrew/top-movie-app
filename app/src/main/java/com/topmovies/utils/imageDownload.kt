package com.topmovies.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500"

fun imageDownload(view: View, url: String, imageView: ImageView) {
    Glide
        .with(view)
        .load("$BASE_URL_IMAGE$url")
        .into(imageView)
}