package com.example.movies.movie_list.data.repository_impl

import com.example.movies.dao.MovieDao
import com.example.movies.api.ApiService
import com.example.movies.core.resource.Resource
import com.example.movies.dao.MovieDetailsDao
import com.example.movies.movie_list.data.toDomain
import com.example.movies.movie_list.data.toEntity
import com.example.movies.movie_list.domain.model.Movie
import com.example.movies.movie_list.domain.repository.MovieRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val apiService: ApiService,
) : MovieRepository {

    override suspend fun refreshMovies(): Resource<List<Movie>> {
        try {

            val movies = apiService.fetchMovies()
            if (movies.isSuccessful)
                movies.body()?.let {
                    movieDao.insertMovies(it.results.map { movie -> movie.toEntity() })
                }
            else return Resource.Error(error = movies.message())
        } catch (exception: Exception) {
            return Resource.Error(error = exception.message ?: "Something went wrong")
        }
        return Resource.Success(
            data = movieDao.getAllMovies().map { movieEntity -> movieEntity.toDomain() })
    }

}