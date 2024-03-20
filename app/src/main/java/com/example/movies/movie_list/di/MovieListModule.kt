package com.example.movies.movie_list.di

import com.example.movies.api.ApiService
import com.example.movies.dao.MovieDao
import com.example.movies.dao.MovieDetailsDao
import com.example.movies.movie_list.data.repository_impl.MovieRepositoryImpl
import com.example.movies.movie_list.domain.repository.MovieRepository
import com.example.movies.movie_list.domain.use_case.MovieListUseCase
import com.example.movies.movie_list.domain.use_case.SearchQueryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(ActivityComponent::class)
@Module
object MovieListModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(
        apiService: ApiService,
        moviesDao: MovieDao,
    ): MovieRepository =
        MovieRepositoryImpl(moviesDao, apiService)

    @Provides
    fun provideMovieListUseCase(movieRepository: MovieRepository): MovieListUseCase =
        MovieListUseCase(movieRepository)

    @Provides
    fun provideSearchUseCase():SearchQueryUseCase = SearchQueryUseCase()
}