package com.topmovies.presentation.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.topmovies.R
import com.topmovies.utils.imageDownload
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailFragment: Fragment() {
    private val viewModel by viewModel<MovieDetailViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        arguments?.let {
            val args = MovieDetailFragmentArgs.fromBundle(it)
            viewModel.getMovie(args.movieId)
        }
        viewModel.apply {
            loading.observe(this@MovieDetailFragment, Observer(::showHideLoading))
            title.observe(this@MovieDetailFragment, Observer(::setMovieTitle))
            description.observe(this@MovieDetailFragment, Observer(::setMovieDescription))
            backdropPath.observe(this@MovieDetailFragment, Observer(::setBackdropImage))
            messageError.observe(this@MovieDetailFragment, Observer(::setMessageError))
        }
    }

    private fun showHideLoading(state: Boolean) {
        movieDetailLoading.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setMovieTitle(title: String) {
        movieDetailTitle.text = title
        activity?.title = title
    }

    private fun setMovieDescription(description: String) {
        movieDetailOverview.text = description
    }

    private fun setBackdropImage(path: String) {
        imageDownload(fragmentMovieDetail, path, movieDetailBanner)
    }

    private fun setMessageError(message: String) {
        Snackbar.make(fragmentMovieDetail, message, Snackbar.LENGTH_LONG).show()
    }

}