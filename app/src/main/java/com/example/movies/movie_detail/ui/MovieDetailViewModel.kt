package com.example.movies.movie_detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.core.resource.Resource
import com.example.movies.movie_detail.data.dto.local.MovieDetailEntity
import com.example.movies.movie_detail.domain.model.MovieDetails
import com.example.movies.movie_detail.domain.use_case.GetMovieDetailByIdUseCase
import com.example.movies.movie_list.domain.model.Movie
import com.example.movies.movie_list.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


data class MovieDetailScreenState(
    val loading: Boolean = false,
    val error: String? = null,
    val data: MovieDetails? = null
)

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieDetailByIdUseCase: GetMovieDetailByIdUseCase) :
    ViewModel() {
    private val _movieDetailState = MutableStateFlow(MovieDetailScreenState())
    val movieDetailState: StateFlow<MovieDetailScreenState> = _movieDetailState

    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            movieDetailByIdUseCase.invoke(movieId)
                .collectLatest { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _movieDetailState.value = MovieDetailScreenState(error = resource.error)

                        }

                        is Resource.Loading -> {
                            _movieDetailState.value = MovieDetailScreenState(loading = true)
                        }

                        is Resource.Success -> {
                            _movieDetailState.value = MovieDetailScreenState(data = resource.data)
                        }
                    }
                }
        }
    }
}
