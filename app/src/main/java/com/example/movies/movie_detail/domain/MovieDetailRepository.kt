package com.example.movies.movie_detail.domain

import com.example.movies.core.resource.Resource
import com.example.movies.movie_detail.domain.model.MovieDetails

interface MovieDetailRepository {
    suspend fun getMovieDetail(movieId: Int): Resource<MovieDetails>
}