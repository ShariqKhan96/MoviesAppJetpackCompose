package com.example.movies.movie_detail.domain.model

import androidx.room.PrimaryKey

data class MovieDetails(
    val id: Int,
    val title: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String?,
    val releaseDate: String?,
    val budget: Int?,
    val runtime: Int?,
    val tagline: String?,
    val homepage: String?,
    val imdbId: String?
)
