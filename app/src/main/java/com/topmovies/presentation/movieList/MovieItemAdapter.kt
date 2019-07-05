package com.topmovies.presentation.movieList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.topmovies.R
import com.topmovies.data.entity.MovieEntity
import com.topmovies.domain.model.Movie
import com.topmovies.utils.imageDownload

class MovieItemAdapter(var movies: List<MovieEntity>, private val listener: OnItemClickListener) : RecyclerView.Adapter<MovieItemAdapter.MovieItemViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(movie: MovieEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false)
        val layoutParams = RecyclerView.LayoutParams((parent as RecyclerView).layoutManager!!.width, ViewGroup.LayoutParams.WRAP_CONTENT)
        rootView.layoutParams = layoutParams
        return MovieItemViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.title
        imageDownload(holder.card, movie.backdropPath, holder.banner)
        holder.bindClickListener(listener, movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var card: View = itemView.findViewById(R.id.movieItemCard)
        internal var title: TextView = itemView.findViewById(R.id.movieItemTitle)
        internal var banner: ImageView = itemView.findViewById(R.id.movieItemBanner)

        fun bindClickListener(listener: OnItemClickListener, movie: MovieEntity) {
            itemView.setOnClickListener { listener.onItemClick(movie) }
        }
    }

}