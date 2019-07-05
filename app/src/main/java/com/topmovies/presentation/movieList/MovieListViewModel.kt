package com.topmovies.presentation.movieList

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.topmovies.data.entity.MovieEntity
import com.topmovies.domain.model.Movie
import com.topmovies.domain.usecase.MovieUseCase
import com.topmovies.presentation.BaseViewModel
import com.topmovies.utils.Resource
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieListViewModel(
    context: Application,
    private val movieUseCase: MovieUseCase,
    coroutineContext: CoroutineContext
): BaseViewModel(context, coroutineContext) {
    val messageError: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val movieList: MutableLiveData<List<MovieEntity>> = MutableLiveData()

    fun getMovieList() {
        viewModelScope.launch {
            loading.postValue(true)
            movieListCallBack(movieUseCase.getTopRatedMovie())
        }
    }

    private fun movieListCallBack(resource: Resource<List<MovieEntity>>) {
        loading.postValue(false)
        when (resource) {
            is Resource.Success -> {
                val list = resource.data
                if (list.isNotEmpty()) {
                    movieList.postValue(list)
                } else {
                    messageError.postValue("No Data")
                }
            }
            is Resource.Error -> {
                messageError.postValue(resource.error)
            }
        }
    }
}