package com.example.movies.movie_detail.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.movies.movie_detail.domain.model.MovieDetails
import com.example.movies.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : ComponentActivity() {
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchMovieDetail(intent.extras!!.getInt("movieId"))
        setContent {
            val movieDetailState = viewModel.movieDetailState.collectAsState().value
            MoviesTheme {
                if (movieDetailState.loading) {
                    CircularProgressIndicator(modifier =  Modifier.
                    fillMaxSize().padding(150.dp))
                }
                if (movieDetailState.data.notNull()) {
                    MovieDetailScreen(movieDetail = movieDetailState.data!!)
                } else if (!movieDetailState.error.isNullOrEmpty()) {
                    Toast.makeText(LocalContext.current, movieDetailState.error, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

}

fun MovieDetails?.notNull(): Boolean {
    return this != null
}
