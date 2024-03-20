package com.example.movies.screen.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.model.MovieDetailEntity
import com.example.movies.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _movieDetail = MutableStateFlow<MovieDetailEntity?>(null)
    val movieDetail: StateFlow<MovieDetailEntity?> = _movieDetail

    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            try {
                val movieDetailResponse = repository.getMovieDetail(movieId)
                _movieDetail.value = movieDetailResponse
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
