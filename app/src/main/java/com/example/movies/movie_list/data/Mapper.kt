package com.example.movies.movie_list.data

import com.example.movies.movie_detail.data.dto.remote.MovieDetailResponse
import com.example.movies.movie_detail.data.dto.local.MovieDetailEntity
import com.example.movies.movie_detail.domain.model.MovieDetails
import com.example.movies.movie_list.data.dto.local.MovieEntity
import com.example.movies.movie_list.domain.model.Movie


fun MovieDetailResponse.toEntity() = MovieDetailEntity(
    id = this.id,
    title = this.title,
    posterPath = this.poster_path,
    backdropPath = this.backdrop_path,
    overview = this.overview,
    releaseDate = this.release_date,
    budget = this.budget,
    runtime = this.runtime,
    tagline = this.tagline,
    homepage = this.homepage,
    imdbId = this.imdb_id
)

fun MovieDetailEntity.toDomain() = MovieDetails(
    id = this.id,
    title = this.title,
    posterPath = this.posterPath,
    backdropPath = this.backdropPath,
    overview = this.overview,
    releaseDate = this.releaseDate,
    budget = this.budget,
    runtime = this.runtime,
    tagline = this.tagline,
    homepage = this.homepage,
    imdbId = this.imdbId
)



fun Movie.toEntity() = MovieEntity(
    vote_average = this.vote_average,
    adult = this.adult,
    backdrop_path = this.backdrop_path,
    genre_ids = this.genre_ids,
    id = this.id,
    video = this.video,
    original_language = this.original_language,
    original_title = this.original_title,
    popularity = this.popularity,
    poster_path = this.poster_path,
    overview = this.overview,
    release_date = this.release_date,
    title = this.title,
    vote_count = this.vote_count
)

fun MovieEntity.toDomain() = Movie(
    vote_average = this.vote_average,
    adult = this.adult,
    backdrop_path = this.backdrop_path,
    genre_ids = this.genre_ids,
    id = this.id,
    video = this.video,
    original_language = this.original_language,
    original_title = this.original_title,
    popularity = this.popularity,
    poster_path = this.poster_path,
    overview = this.overview,
    release_date = this.release_date,
    title = this.title,
    vote_count = this.vote_count
)

//infix fun MovieDetailResponse.asEntity()= MovieDetailEntity()