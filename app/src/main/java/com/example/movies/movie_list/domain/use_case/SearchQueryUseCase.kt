package com.example.movies.movie_list.domain.use_case

import com.example.movies.movie_list.domain.model.Movie
import javax.inject.Inject

class SearchQueryUseCase @Inject constructor() {
    operator fun invoke(query: String, list: List<Movie>): List<Movie> {
        return list.filter { movie -> movie.title.contains(query, ignoreCase = true) }
    }
}