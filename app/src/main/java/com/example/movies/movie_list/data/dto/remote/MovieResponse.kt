package com.example.movies.movie_list.data.dto.remote

import com.example.movies.movie_list.domain.model.Movie

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)



