package com.example.movies.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.model.MovieDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movieDetail: MovieDetailEntity)

    @Query("SELECT * FROM movie_detail WHERE id = :movieId")
    fun getMovieDetail(movieId: Int): MovieDetailEntity
}
