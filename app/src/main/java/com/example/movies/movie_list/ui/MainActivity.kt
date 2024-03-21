package com.example.movies.movie_list.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movies.movie_detail.ui.MovieItem
import com.example.movies.movie_detail.ui.MovieDetailsActivity
import com.example.movies.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val movieState = viewModel.movieList.collectAsState().value
            val searchQuery = viewModel.searchQuery.collectAsState().value


            MoviesTheme {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    // Search bar
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { query ->
                            viewModel.updateSearchQuery(query)
                        },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        decorationBox = { innerTextField ->
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .fillMaxWidth()
                                    .background(
                                        color = Color(0xFFFFFFFF),
                                        shape = RoundedCornerShape(size = 16.dp)
                                    )
                                    .border(
                                        width = 2.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(size = 16.dp)
                                    )
                                    .padding(all = 16.dp),

                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search icon",
                                    tint = Color.DarkGray
                                )
                                Spacer(modifier = Modifier.width(width = 8.dp))
                                innerTextField()
                            }
                        }
                    )

                    if (movieState.loading) {
                        CircularProgressIndicator(modifier = Modifier.fillMaxSize().padding(150.dp))
                    }
                    if (movieState.data.isNotEmpty()) {
                        LazyColumn {
                            items(movieState.data) { movie ->
                                MovieItem(
                                    movie = movie,
                                    baseUrl = "https://image.tmdb.org/t/p/w500"
                                ) { movieId ->
                                    launchDetailActivity(movieId)
                                }
                            }
                        }
                    } else if (!movieState.error.isNullOrEmpty()) {
                        Toast.makeText(LocalContext.current, movieState.error, Toast.LENGTH_LONG)
                            .show()
                        Log.e("Error",movieState.error)
                    }


                }
            }
        }
    }

    private fun launchDetailActivity(movieId: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("movieId", movieId)
        startActivity(intent)
    }
}