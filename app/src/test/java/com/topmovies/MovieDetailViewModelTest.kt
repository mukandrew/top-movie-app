package com.topmovies

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.topmovies.application.AppContext
import com.topmovies.data.MovieRepository
import com.topmovies.domain.mapper.toMovieModel
import com.topmovies.domain.usecase.MovieUseCase
import com.topmovies.presentation.movieDetail.MovieDetailViewModel
import com.topmovies.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MovieDetailViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var context: Application
    @Mock
    private lateinit var mockMovieRepository: MovieRepository
    @Mock
    private lateinit var mockMovieUseCase: MovieUseCase
    @Mock
    private lateinit var loadingTest: Observer<Boolean>
    @Mock
    private lateinit var titleTest: Observer<String>
    @Mock
    private lateinit var descriptionTest: Observer<String>
    @Mock
    private lateinit var backDropPathTest: Observer<String>
    @Mock
    private lateinit var snackBarTest: Observer<String>
    
    private lateinit var movieDetailViewModel: MovieDetailViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        context = mock(AppContext::class.java)

        snackBarTest = mock(Observer::class.java) as Observer<String>
        loadingTest = mock(Observer::class.java) as Observer<Boolean>
        titleTest = mock(Observer::class.java) as Observer<String>
        descriptionTest = mock(Observer::class.java) as Observer<String>
        backDropPathTest = mock(Observer::class.java) as Observer<String>
        
        mockMovieRepository = mock(MovieRepository::class.java)
        mockMovieUseCase = spy(MovieUseCase(mockMovieRepository))

        movieDetailViewModel = MovieDetailViewModel(context, mockMovieUseCase, Dispatchers.Default)
        with(movieDetailViewModel) {
            snackBar.observeForever(snackBarTest)
            loading.observeForever(loadingTest)
            title.observeForever(titleTest)
            description.observeForever(descriptionTest)
            backdropPath.observeForever(backDropPathTest)
        }
    }
    
    @Test
    fun getMovieDetail_Success() = runBlocking {
        `when`(mockMovieRepository.getMovie(MOVIE_ID)).thenReturn(Resource.Success(MOVIE_DETAIL_DATA))
        movieDetailViewModel.getMovie(MOVIE_ID)
        verify(loadingTest, atLeastOnce()).onChanged(true)
        verify(loadingTest, atLeastOnce()).onChanged(false)
        verify(mockMovieUseCase, atLeastOnce()).getMovie(MOVIE_ID)
        val movieDetail = MOVIE_DETAIL_DATA.toMovieModel()
        verify(titleTest, atLeastOnce()).onChanged(movieDetail.title)
        verify(descriptionTest, atLeastOnce()).onChanged(movieDetail.overview)
        verify(backDropPathTest, atLeastOnce()).onChanged(movieDetail.backdropPath)
        verify(snackBarTest, never()).onChanged(null)
    }

    @Test
    fun getMovieDetail_Error() = runBlocking {
        `when`(mockMovieRepository.getMovie(MOVIE_ID)).thenReturn(Resource.Error("ID NOT FOUND"))
        movieDetailViewModel.getMovie(MOVIE_ID)
        verify(loadingTest, atLeastOnce()).onChanged(true)
        verify(loadingTest, atLeastOnce()).onChanged(false)
        verify(mockMovieUseCase, atLeastOnce()).getMovie(MOVIE_ID)
        verify(titleTest, never()).onChanged(null)
        verify(descriptionTest, never()).onChanged(null)
        verify(backDropPathTest, never()).onChanged(null)
        verify(snackBarTest, atLeastOnce()).onChanged("ID NOT FOUND")
    }
}