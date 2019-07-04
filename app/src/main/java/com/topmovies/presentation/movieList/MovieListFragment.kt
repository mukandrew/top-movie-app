package com.topmovies.presentation.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.topmovies.R
import com.topmovies.domain.model.Movie
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListFragment: Fragment() {

    private val viewModel by viewModel<MovieListViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Top Rated Movies"

        movieListRecyclerView.setHasFixedSize(true)
        movieListRecyclerView.adapter = MovieItemAdapter(listOf(), onClickMovie)
        movieListRecyclerView.layoutManager = LinearLayoutManager(activity)

        initData()
    }

    private fun initData() {
        viewModel.getMovieList()
        viewModel.apply {
            movieList.observe(this@MovieListFragment, Observer(::setRecyclerView))
            loading.observe(this@MovieListFragment, Observer(::showHideLoading))
            snackbar.observe(this@MovieListFragment, Observer(::showMessageError))
        }
    }

    private fun showHideLoading(state: Boolean) {
        movieListLoading.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setRecyclerView(movieList: List<Movie>) {
        (movieListRecyclerView.adapter as MovieItemAdapter).movies = movieList
        (movieListRecyclerView.adapter as MovieItemAdapter).notifyDataSetChanged()
    }

    private fun showMessageError(message: String) {
        Snackbar.make(fragmentMovieList, message, Snackbar.LENGTH_LONG).show()
    }

    private val onClickMovie = object : MovieItemAdapter.OnItemClickListener {
        override fun onItemClick(movie: Movie) {
            val navigation = Navigation.findNavController(activity!!, R.id.mainNavigation)
            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment()
            action.movieId = movie.id.toString()
            navigation.navigate(action)
        }
    }
}