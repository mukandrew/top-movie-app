package com.topmovies.presentation.movieDetail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.topmovies.data.entity.MovieEntity
import com.topmovies.domain.usecase.MovieUseCase
import com.topmovies.presentation.BaseViewModel
import com.topmovies.utils.Resource
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieDetailViewModel(
    context: Application,
    private val movieUseCase: MovieUseCase,
    coroutineContext: CoroutineContext
): BaseViewModel(context, coroutineContext) {

    val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val title: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val backdropPath: MutableLiveData<String> = MutableLiveData()
    val messageError: MutableLiveData<String> = MutableLiveData()

    fun getMovie(movieId: String) {
        viewModelScope.launch {
            loading.postValue(true)
            getMovieCallBack(movieUseCase.getMovie(movieId))
        }
    }

    private fun getMovieCallBack(resource: Resource<MovieEntity>) {
        loading.postValue(false)
        when (resource) {
            is Resource.Success -> postSuccess(resource.data)
            is Resource.Error -> messageError.postValue(resource.error)
        }
    }

    private fun postSuccess(movie: MovieEntity) {
        title.postValue(movie.title)
        description.postValue(movie.overview)
        backdropPath.postValue(movie.backdropPath)
    }
}