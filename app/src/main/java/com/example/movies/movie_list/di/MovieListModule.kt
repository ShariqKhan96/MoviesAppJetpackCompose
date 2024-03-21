package com.example.movies.movie_list.di

import com.example.movies.core.api.ApiService
import com.example.movies.core.db.dao.MovieDao
import com.example.movies.core.db.dao.MovieDetailsDao
import com.example.movies.core.manager.ConnectivityManager
import com.example.movies.movie_list.data.repository_impl.MovieRepositoryImpl
import com.example.movies.movie_list.domain.repository.MovieRepository
import com.example.movies.movie_list.domain.use_case.MovieListUseCase
import com.example.movies.movie_list.domain.use_case.SearchQueryUseCase
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
object MovieListModule {

    @Provides
    fun provideMoviesRepository(
        apiService: ApiService,
        moviesDao: MovieDao,
        connectivityManager: ConnectivityManager
    ): MovieRepository = MovieRepositoryImpl(moviesDao, apiService, connectivityManager)


    @Provides
    fun provideMovieListUseCase(movieRepository: MovieRepository): MovieListUseCase =
        MovieListUseCase(movieRepository)

    @Provides
    fun provideSearchUseCase():SearchQueryUseCase = SearchQueryUseCase()
}