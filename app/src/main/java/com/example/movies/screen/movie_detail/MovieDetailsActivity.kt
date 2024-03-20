package com.example.movies.screen.movie_detail

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.example.movies.composables.MovieDetailScreen
import com.example.movies.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : ComponentActivity() {
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchMovieDetail(intent.extras!!.getInt("movieId"))
        setContent {
            val movieDetail = viewModel.movieDetail.collectAsState(null).value
//            Log.d("hamza", "size = " + movieDetail?.title)
            MoviesTheme {
                movieDetail?.let {
                    MovieDetailScreen(it)
                }
            }
        }
    }

}
