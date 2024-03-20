package com.example.movies.movie_detail.di

import com.example.movies.api.ApiService
import com.example.movies.dao.MovieDao
import com.example.movies.dao.MovieDetailsDao
import com.example.movies.movie_detail.data.repository_impl.MovieDetailRepositoryImpl
import com.example.movies.movie_detail.domain.MovieDetailRepository
import com.example.movies.movie_detail.domain.use_case.GetMovieDetailByIdUseCase
import com.example.movies.movie_list.data.repository_impl.MovieRepositoryImpl
import com.example.movies.movie_list.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MovieDetailModule {

    @Provides
    @Singleton
    fun provideMovieDetailRepository(
        apiService: ApiService,
        movieDetailsDao: MovieDetailsDao,
    ): MovieDetailRepository =
        MovieDetailRepositoryImpl(apiService, movieDetailsDao, Dispatchers.Main)


    @Provides
    fun provideMovieDetailUseCase(movieDetailRepository: MovieDetailRepository):
            GetMovieDetailByIdUseCase = GetMovieDetailByIdUseCase(movieDetailRepository)
}