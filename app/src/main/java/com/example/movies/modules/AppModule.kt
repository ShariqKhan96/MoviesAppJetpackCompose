package com.example.movies.modules

import android.content.Context
import androidx.room.Room
import com.example.movies.AppDatabase
import com.example.movies.dao.MovieDao
import com.example.movies.api.ApiService
import com.example.movies.dao.MovieDetailsDao
import com.example.movies.repository.MovieRepository
import com.example.movies.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
        fun provideMoviesRepository(apiService: ApiService, moviesDao: MovieDao, movieDetailsDao: MovieDetailsDao, couroutineScope : CoroutineDispatcher): MovieRepository = MovieRepositoryImpl(moviesDao, apiService, movieDetailsDao, Dispatchers.Main)


    @Provides
    @Singleton
    fun provideCoroutineScope(dispatcher: CoroutineDispatcher): CoroutineScope {
        return CoroutineScope(SupervisorJob() + dispatcher)
    }

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideAppDao(
        @ApplicationContext appContext
        : Context
    ): AppDatabase {

        return Room.databaseBuilder(
            appContext, AppDatabase::class.java, "Movies"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }



    @Provides
    @Singleton
    fun provideMoviesDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    @Singleton
    fun provideMovieDetailsDao(database: AppDatabase): MovieDetailsDao {
        return database.movieDetailsDao()
    }
}
