package com.example.movies.movie_detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.movie_detail.data.dto.local.MovieDetailEntity
import com.example.movies.movie_detail.domain.MovieDetailRepository
import com.example.movies.movie_detail.domain.model.MovieDetails

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestMovieDetailViewModel @Inject constructor(private val repository: MovieDetailRepository) : ViewModel() {
    private val _movieDetail = MutableStateFlow<MovieDetails?>(null)
    val movieDetail: StateFlow<MovieDetails?> = _movieDetail

    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            try {
                val movieDetailResponse = repository.getMovieDetail(movieId)
                _movieDetail.value = movieDetailResponse.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
