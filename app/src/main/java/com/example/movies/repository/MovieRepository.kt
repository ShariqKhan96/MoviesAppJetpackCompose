package com.example.movies.repository

import com.example.movies.Movie
import com.example.movies.model.MovieDetailEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun refreshMovies() : Flow<List<Movie>>
    suspend fun getMovieDetail(movieId : Int) : MovieDetailEntity?
}