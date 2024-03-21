package com.example.movies.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movies.core.db.dao.MovieDao
import com.example.movies.core.db.dao.MovieDetailsDao
import com.example.movies.movie_detail.data.dto.local.MovieDetailEntity
import com.example.movies.movie_list.data.dto.local.MovieEntity


@Database(entities = [MovieEntity::class, MovieDetailEntity::class], version = 3, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieDetailsDao(): MovieDetailsDao

}