package com.example.movies.movie_list.domain.repository

import com.example.movies.core.resource.Resource
import com.example.movies.model.MovieDetailEntity
import com.example.movies.movie_list.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun refreshMovies(): Resource<List<Movie>>
  //  suspend fun getMovieDetail(movieId: Int): Resource<Movie>
}