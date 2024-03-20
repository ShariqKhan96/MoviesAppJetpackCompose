package com.example.movies.screen.movie_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.movies.composables.MovieItem
import com.example.movies.screen.movie_detail.MovieDetailsActivity
import com.example.movies.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val movies = viewModel.filteredMovies.collectAsState(emptyList()).value
            Log.d("hamza", "size = " + movies.size)
            var searchQuery  =  viewModel.searchQuery.collectAsState().value


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
                        textStyle = TextStyle(color = Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                                decorationBox = { innerTextField ->
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)
                                    .fillMaxWidth()
                                    .background(color = Color(0xFFD2F3F2), shape = RoundedCornerShape(size = 16.dp))
                                    .border(
                                        width = 2.dp,
                                        color = Color.Cyan,
                                        shape = RoundedCornerShape(size = 16.dp)
                                    )
                                    .padding(all = 16.dp),

                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Favorite icon",
                                    tint = Color.DarkGray
                                )
                                Spacer(modifier = Modifier.width(width = 8.dp))
                                innerTextField()
                            }
                        }
                    )


                    LazyColumn {
                        items(movies) { movie ->
                            MovieItem(
                                movie = movie,
                                baseUrl = "https://image.tmdb.org/t/p/w500"
                            ) { movieId ->
                                launchDetailActivity(movieId)
                            }
                        }
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