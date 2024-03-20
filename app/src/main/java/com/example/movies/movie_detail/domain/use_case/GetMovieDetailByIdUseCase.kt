package com.example.movies.movie_detail.domain.use_case

import com.example.movies.core.resource.Resource
import com.example.movies.movie_detail.domain.MovieDetailRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailByIdUseCase @Inject constructor(private val movieDetailRepository: MovieDetailRepository) {
    suspend operator fun invoke(movieId: Int) = flow {
        emit(Resource.Loading(isLoading = true))
        val response = movieDetailRepository.getMovieDetail(movieId)
        if (response.error.isNullOrEmpty())
            emit(Resource.Success(data = response.data))
        else emit(Resource.Error(error = response.error))
    }
}