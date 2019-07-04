package com.topmovies.application

import androidx.room.Room
import com.topmovies.data.MovieRepository
import com.topmovies.data.local.AppDataBase
import com.topmovies.data.local.MovieLocalDataSource
import com.topmovies.data.remote.MovieAPI
import com.topmovies.data.remote.MovieRemoteDataSource
import com.topmovies.data.remote.RetrofitFactory
import com.topmovies.domain.usecase.MovieUseCase
import com.topmovies.presentation.movieDetail.MovieDetailViewModel
import com.topmovies.presentation.movieList.MovieListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object KoinModules {
    private val mainCoroutineDispatcher by lazy { Dispatchers.Default }

    val mainModule = module {
        single { AppContext }
    }

    val dbModule = module {
        single { Room.databaseBuilder(
            androidApplication(),
            AppDataBase::class.java,
            "TopMovieDB").fallbackToDestructiveMigration().build() }
        single { get<AppDataBase>().movieDao() }
    }

    val movieModule = module {
        single { MovieRemoteDataSource(RetrofitFactory.getRetrofit()) }
        single { MovieLocalDataSource(get()) }
        single { MovieRepository(get(), get()) }
        single { MovieUseCase(get()) }
        viewModel { MovieListViewModel(get(), get(), mainCoroutineDispatcher) }
        viewModel { MovieDetailViewModel(get(), get(), mainCoroutineDispatcher) }
    }
}