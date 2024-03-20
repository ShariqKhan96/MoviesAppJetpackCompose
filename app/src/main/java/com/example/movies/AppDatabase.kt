package com.example.movies

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movies.dao.MovieDao
import com.example.movies.dao.MovieDetailsDao
import com.example.movies.model.MovieDetailEntity


@Database(entities = [Movie::class, MovieDetailEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieDetailsDao(): MovieDetailsDao

}