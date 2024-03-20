package com.example.movies.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_detail")
data class MovieDetailEntity    (
    @PrimaryKey val id: Int,
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
