package com.example.movies.movie_list.data.repository_impl

import android.content.Context
import com.example.movies.core.db.dao.MovieDao
import com.example.movies.core.api.ApiService
import com.example.movies.core.resource.Resource
import com.example.movies.core.db.dao.MovieDetailsDao
import com.example.movies.core.manager.ConnectivityManager
import com.example.movies.movie_list.data.toDomain
import com.example.movies.movie_list.data.toEntity
import com.example.movies.movie_list.domain.model.Movie
import com.example.movies.movie_list.domain.repository.MovieRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val apiService: ApiService,
    private val connectivityManager: ConnectivityManager
) : MovieRepository {

    override suspend fun refreshMovies(): Resource<List<Movie>> {
//        if (!connectivityManager.isNetworkConnected()) {
//            val list = movieDao.getAllMovies()
//            return if (list.isNotEmpty()) {
//                Resource.Success(
//                    data = list
//                        .map { movieEntity -> movieEntity.toDomain() })
//            } else {
//                Resource.Error(error = "No internet connection available.")
//            }
//        }
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