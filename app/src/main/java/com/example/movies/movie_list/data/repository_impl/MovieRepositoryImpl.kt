package com.example.movies.movie_list.data.repository_impl

import com.example.movies.dao.MovieDao
import com.example.movies.api.ApiService
import com.example.movies.api.MovieDetailResponse
import com.example.movies.core.resource.Resource
import com.example.movies.dao.MovieDetailsDao
import com.example.movies.model.MovieDetailEntity
import com.example.movies.movie_list.data.dto.local.MovieEntity
import com.example.movies.movie_list.data.toDomain
import com.example.movies.movie_list.data.toEntity
import com.example.movies.movie_list.domain.model.Movie
import com.example.movies.movie_list.domain.repository.MovieRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val apiService: ApiService,
    private val movieDetailsDao: MovieDetailsDao,
    private val main: CoroutineDispatcher
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

//    override suspend fun getMovieDetail(movieId: Int): Resource<MovieDetail> {
//        try {
//            val remoteMovieDetail = apiService.getMovieDetails(movieId)
//            if (remoteMovieDetail.isSuccessful) {
//                remoteMovieDetail.body()?.let {
//                    saveMovieDetail(remoteMovieDetail.body()!!)
//                }
//            }
//        } catch (exception: Exception) {
//        }
//        return getMovieDetailFromDb(movieId)
//    }
//
//    private suspend fun getMovieDetailFromDb(id: Int): MovieDetailEntity? {
//        return withContext(main) {
//            movieDetailsDao.getMovieDetail(id)
//        }
//    }
//
//
//    private suspend fun saveMovieDetail(movieDetailResponse: MovieDetailResponse) {
//        movieDetailsDao.insertMovieDetail(movieDetailResponse.toEntity())
//    }

}