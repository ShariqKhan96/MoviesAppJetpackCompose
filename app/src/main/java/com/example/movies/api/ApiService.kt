package com.example.movies.api

import com.example.movies.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("3/movie/popular")
    suspend fun fetchMovies(
        @Query("api_key") apiKey: String = "0e79223a20635e52908bf9ee269b267a"
    ):Response<MovieResponse>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "0e79223a20635e52908bf9ee269b267a"
    ): Response<MovieDetailResponse>
}
