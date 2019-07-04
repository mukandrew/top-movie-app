package com.topmovies.presentation.movieDetail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.topmovies.domain.model.Movie
import com.topmovies.domain.usecase.MovieUseCase
import com.topmovies.presentation.BaseViewModel
import com.topmovies.utils.Resource
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieDetailViewModel(
    val context: Application,
    val movieUseCase: MovieUseCase,
    val coroutineContext: CoroutineContext
): BaseViewModel(context, coroutineContext) {

    val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    var movie: Movie? = null
    val title: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val backdropPath: MutableLiveData<String> = MutableLiveData()
    val snackBar: MutableLiveData<String> = MutableLiveData()

    fun getMovie(movieId: String) {
        viewModelScope.launch {
            loading.postValue(true)
            getMovieCallBack(movieUseCase.getMovie(movieId))
        }
    }

    private fun getMovieCallBack(resource: Resource<Movie>) {
        loading.postValue(false)
        when (resource) {
            is Resource.Success -> {
                movie = resource.data
                title.postValue(movie!!.title)
                description.postValue(movie!!.overview)
                backdropPath.postValue(movie!!.backdropPath)
            }
            is Resource.Error -> {
                snackBar.postValue(resource.error)
            }
        }
    }
}