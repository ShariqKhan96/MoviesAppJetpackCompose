package com.example.movies.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.movies.api.Genre
import com.example.movies.api.MovieDetailResponse
import com.example.movies.model.MovieDetailEntity
@Composable
fun MovieDetailScreen(movieDetail: MovieDetailEntity) {
    val baseUrl = "https://image.tmdb.org/t/p/w500"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            if (!movieDetail.posterPath.isNullOrBlank()) {
                Image(
                    painter = rememberImagePainter("$baseUrl${movieDetail.posterPath}"),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movieDetail.title!!,
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Release Date: ${movieDetail.releaseDate}",
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Budget: $${movieDetail.budget}",
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Overview:",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        Text(
            text = movieDetail.overview ?: "",
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Genres:",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )

//        LazyRow(
//            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
//        ) {
//            items(movieDetail) { genre ->
//                Chip(text = genre.name)
//            }
//        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Production Companies:",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )
//
//        LazyColumn(
//            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
//        ) {
//            items(movieDetail.productionCompanies) { company ->
//                Text(
//                    text = company.name,
//                    style = TextStyle(fontSize = 16.sp)
//                )
//            }
//        }
    }
}

@Composable
fun Chip(text: String) {
    Box(
        modifier = Modifier
            .background(Color(0xFFE5E7EB))
            .padding(8.dp)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(50))
            .clip(RoundedCornerShape(50))
    ) {
        Text(
            text = text,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}
