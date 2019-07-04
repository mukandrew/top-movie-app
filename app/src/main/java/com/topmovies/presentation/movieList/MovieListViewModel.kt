package com.topmovies.presentation.movieList

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Ignore
import com.topmovies.domain.model.Movie
import com.topmovies.domain.usecase.MovieUseCase
import com.topmovies.presentation.BaseViewModel
import com.topmovies.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MovieListViewModel(
    val context: Application,
    val movieUseCase: MovieUseCase,
    val coroutineContext: CoroutineContext
): BaseViewModel(context, coroutineContext) {

    val snackbar: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val movieList: MutableLiveData<List<Movie>> = MutableLiveData()

    fun getMovieList() {
        viewModelScope.launch {
            loading.postValue(true)
            movieListCallBack(movieUseCase.getTopRatedMovie())
        }
    }

    private fun movieListCallBack(resource: Resource<List<Movie>>) {
        loading.postValue(false)
        when (resource) {
            is Resource.Success -> {
                val list = resource.data
                if (list.isNotEmpty()) {
                    movieList.postValue(list)
                } else {
                    getMovieList()
                }
            }
            is Resource.Error -> {
                snackbar.postValue(resource.error)
            }
        }
    }
}