package com.example.movies.movie_detail.data.repository_impl

import com.example.movies.core.api.ApiService
import com.example.movies.core.resource.Resource
import com.example.movies.core.db.dao.MovieDetailsDao
import com.example.movies.core.manager.ConnectivityManager
import com.example.movies.movie_detail.data.dto.local.MovieDetailEntity
import com.example.movies.movie_detail.data.dto.remote.MovieDetailResponse
import com.example.movies.movie_detail.domain.MovieDetailRepository
import com.example.movies.movie_detail.domain.model.MovieDetails
import com.example.movies.movie_list.data.toDomain
import com.example.movies.movie_list.data.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val movieDetailsDao: MovieDetailsDao,
    private val connectivityManager: ConnectivityManager,
    private val main: CoroutineDispatcher
) : MovieDetailRepository {
    override suspend fun getMovieDetail(movieId: Int): Resource<MovieDetails> {

//        if (!connectivityManager.isNetworkConnected()) {
//            val obj = getMovieDetailFromDb(movieId)?.toDomain()
//            obj?.let {
//                return Resource.Success(
//                    data = obj
//                )
//            } ?: run {
//                return Resource.Error(error = "No internet connection available.")
//            }
//        }
        try {
            val remoteMovieDetail = apiService.getMovieDetails(movieId)
            if (remoteMovieDetail.isSuccessful) {
                remoteMovieDetail.body()?.let {
                    saveMovieDetail(remoteMovieDetail.body()!!)
                }
            } else {
                return Resource.Error(error = remoteMovieDetail.message())
            }
        } catch (exception: Exception) {
            return Resource.Error(error = exception.message.orEmpty())
        }
        return Resource.Success(data = getMovieDetailFromDb(movieId)?.toDomain())
    }

    private suspend fun getMovieDetailFromDb(id: Int): MovieDetailEntity? {
        return withContext(main) {
            movieDetailsDao.getMovieDetail(id)
        }
    }


    private suspend fun saveMovieDetail(movieDetailResponse: MovieDetailResponse) {
        movieDetailsDao.insertMovieDetail(movieDetailResponse.toEntity())
    }

}