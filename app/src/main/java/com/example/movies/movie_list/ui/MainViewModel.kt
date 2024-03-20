package com.example.movies.movie_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.core.resource.Resource
import com.example.movies.movie_list.domain.model.Movie
import com.example.movies.movie_list.domain.repository.MovieRepository
import com.example.movies.movie_list.domain.use_case.MovieListUseCase
import com.example.movies.movie_list.domain.use_case.SearchQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


data class MovieListScreenState(
    val loading: Boolean = false,
    val error: String? = null,
    val data: List<Movie> = emptyList()
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase,
    private val searchQueryUseCase: SearchQueryUseCase
) : ViewModel() {

    private val _movieList = MutableStateFlow(MovieListScreenState())
    val movieList: StateFlow<MovieListScreenState> = _movieList
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val actualMovieList = mutableListOf<Movie>()

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {

            movieListUseCase().collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _movieList.value = MovieListScreenState(error = resource.error)
                    }

                    is Resource.Loading -> {
                        _movieList.value = MovieListScreenState(loading = true)
                    }

                    is Resource.Success -> {
                        _movieList.value = MovieListScreenState(data = resource.data.orEmpty())
                        actualMovieList.clear()
                        actualMovieList.addAll(_movieList.value.data)
                    }
                }
            }
        }
    }

//    val filteredMovies: Flow<List<Movie>> = combine(_movieList, searchQuery) { movieList, query ->
//        if (query.isBlank()) {
//            return@combine movieList.data
//        } else {
//            return@combine movieList.data.filter { it.title.contains(query, ignoreCase = true) }
//        }
//    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if (_searchQuery.value.isBlank())
            _movieList.value = MovieListScreenState(data = actualMovieList)
        else
            _movieList.value = MovieListScreenState(
                data = searchQueryUseCase.invoke(
                    _searchQuery.value,
                    _movieList.value.data
                )
            )
    }
}