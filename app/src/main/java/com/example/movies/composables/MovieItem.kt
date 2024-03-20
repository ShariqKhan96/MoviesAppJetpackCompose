package com.example.movies.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.movies.Movie

@Composable
fun MovieItem(
    movie: Movie,
    baseUrl: String,
    onItemClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { onItemClick(movie.id) },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Movie poster

            Log.d("imageUrls","$baseUrl${movie.poster_path}")
            Image(
                painter = rememberImagePainter("$baseUrl${movie.poster_path}"),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(150.dp)
                    .height(180.dp)
                    .padding(20.dp)
//                    .clip(CircleShape)
            )

            // Movie details
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Release Date: ${movie.release_date}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 4.dp)
                )
            }
        }
    }
}
