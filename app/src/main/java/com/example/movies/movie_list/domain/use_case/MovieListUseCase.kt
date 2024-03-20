package com.example.movies.movie_list.domain.use_case

import com.example.movies.core.resource.Resource
import com.example.movies.movie_list.domain.repository.MovieRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieListUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke() = flow {
        emit(Resource.Loading(isLoading = true))
        val response = movieRepository.refreshMovies()
        if (response.error.isNullOrEmpty())
            emit(Resource.Success(data = response.data))
        else emit(Resource.Error(error = response.error))
    }
}