package com.topmovies

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.topmovies.application.AppContext
import com.topmovies.data.MovieRepository
import com.topmovies.domain.mapper.toMovieModel
import com.topmovies.domain.model.Movie
import com.topmovies.domain.usecase.MovieUseCase
import com.topmovies.presentation.movieList.MovieListViewModel
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

class MovieListViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var context: Application
    @Mock
    private lateinit var mockMovieRepository: MovieRepository
    @Mock
    private lateinit var mockMovieUseCase: MovieUseCase
    @Mock
    private lateinit var snackbarTest: Observer<String>
    @Mock
    private lateinit var loadingTest: Observer<Boolean>
    @Mock
    private lateinit var movieListTest: Observer<List<Movie>>

    private lateinit var movieListViewModel: MovieListViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        context = mock(AppContext::class.java)

        snackbarTest = mock(Observer::class.java) as Observer<String>
        loadingTest = mock(Observer::class.java) as Observer<Boolean>
        movieListTest = mock(Observer::class.java) as Observer<List<Movie>>

        mockMovieRepository = mock(MovieRepository::class.java)
        mockMovieUseCase = spy(MovieUseCase(mockMovieRepository))

        movieListViewModel = MovieListViewModel(context, mockMovieUseCase, Dispatchers.Default)
        with(movieListViewModel) {
            messageError.observeForever(snackbarTest)
            loading.observeForever(loadingTest)
            movieList.observeForever(movieListTest)
        }
    }

    @Test
    fun getMovieList_Success() = runBlocking {
        `when`(mockMovieRepository.getTopRatedMovie()).thenReturn(Resource.Success(LIST_MOVIE_DATA))
        movieListViewModel.getMovieList()
        verify(loadingTest, atLeastOnce()).onChanged(true)
        verify(loadingTest, atLeastOnce()).onChanged(true)
        verify(mockMovieUseCase, atLeastOnce()).getTopRatedMovie()
        val listMovie = LIST_MOVIE_DATA.map { it.toMovieModel() }
        verify(movieListTest, atLeastOnce()).onChanged(listMovie)
        verify(snackbarTest, never()).onChanged(null)
    }

    @Test
    fun getMovieList_Error() = runBlocking {
        `when`(mockMovieRepository.getTopRatedMovie()).thenReturn(Resource.Error("Error Unit Test"))
        movieListViewModel.getMovieList()
        verify(loadingTest, atLeastOnce()).onChanged(true)
        verify(loadingTest, atLeastOnce()).onChanged(true)
        verify(mockMovieUseCase, atLeastOnce()).getTopRatedMovie()
        verify(movieListTest, never()).onChanged(null)
        verify(snackbarTest, atLeastOnce()).onChanged("Error Unit Test")
    }
}