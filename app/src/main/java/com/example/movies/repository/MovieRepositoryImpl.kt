package com.example.movies.repository

import android.util.Log
import com.example.movies.Movie
import com.example.movies.dao.MovieDao
import com.example.movies.api.ApiService
import com.example.movies.api.MovieDetailResponse
import com.example.movies.dao.MovieDetailsDao
import com.example.movies.model.MovieDetailEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val apiService: ApiService,
    private val movieDetailsDao: MovieDetailsDao,
    private val main: CoroutineDispatcher
) : MovieRepository {

    override suspend fun refreshMovies(): Flow<List<Movie>> {
        try {
            val movies = apiService.fetchMovies()
            if (movies.isSuccessful)
                movies.body()?.let {
                    movieDao.insertMovies(it.results)
                }
        } catch (exception: Exception) {
        }
        return movieDao.getAllMovies()
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailEntity? {
        try {
            val remoteMovieDetail = apiService.getMovieDetails(movieId)
            if (remoteMovieDetail.isSuccessful) {
                remoteMovieDetail.body()?.let {
                    saveMovieDetail(remoteMovieDetail.body()!!)
                }
            }
        } catch (exception: Exception) {
        }
        return getMovieDetailFromDb(movieId)
    }

    private suspend fun getMovieDetailFromDb(id: Int): MovieDetailEntity? {
        return withContext(main) {
            movieDetailsDao.getMovieDetail(id)
        }
    }


    private suspend fun saveMovieDetail(movieDetailResponse: MovieDetailResponse) {
        val movieDetailEntity = MovieDetailEntity(
            id = movieDetailResponse.id,
            title = movieDetailResponse.title,
            posterPath = movieDetailResponse.poster_path,
            backdropPath = movieDetailResponse.backdrop_path,
            overview = movieDetailResponse.overview,
            releaseDate = movieDetailResponse.release_date,
            budget = movieDetailResponse.budget,
            runtime = movieDetailResponse.runtime,
            tagline = movieDetailResponse.tagline,
            homepage = movieDetailResponse.homepage,
            imdbId = movieDetailResponse.imdb_id
        )

        movieDetailsDao.insertMovieDetail(movieDetailEntity)
    }

}