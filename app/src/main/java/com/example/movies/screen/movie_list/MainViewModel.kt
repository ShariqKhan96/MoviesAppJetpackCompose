package com.example.movies.screen.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.Movie
import com.example.movies.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _movieList = MutableStateFlow<List<Movie>>(emptyList())
    val movieList: StateFlow<List<Movie>> = _movieList
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch(dispatcher) {
             repository.refreshMovies().collectLatest { list->
                 _movieList.value = list
             }
        }
    }

    val filteredMovies: Flow<List<Movie>> = combine(movieList, searchQuery) { movies, query ->
        if (query.isBlank()) {
            return@combine movies
        } else {
            return@combine movies.filter { it.title.contains(query, ignoreCase = true) }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}