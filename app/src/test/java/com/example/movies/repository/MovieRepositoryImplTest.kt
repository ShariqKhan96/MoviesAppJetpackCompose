package com.example.movies.repository


import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movies.Movie
import com.example.movies.MovieResponse
import com.example.movies.api.*
import com.example.movies.dao.MovieDao
import com.example.movies.dao.MovieDetailsDao
import com.example.movies.model.MovieDetailEntity
import junit.framework.Assert.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieRepositoryImplTest {
    private lateinit var repository: MovieRepositoryImpl

    @Mock
    private lateinit var movieDao: MovieDao

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var movieDetailsDao: MovieDetailsDao

    private val testMovies = listOf(
        Movie(
            adult = false,
            backdrop_path = "/ctMserH8g2SeOAnCw5gFjdQF8mo.jpg",
            genre_ids = listOf(35, 12, 14),
            id = 346698,
            original_language = "en",
            original_title = "Barbie",
            overview = "Barbie and Ken are having the time of their lives in the colorful and seemingly perfect world of Barbie Land. However, when they get a chance to go to the real world, they soon discover the joys and perils of living among humans.",
            popularity = 1069.34,
            poster_path = "/iuFNMS8U5cb6xfzi51Dbkovj7vM.jpg",
            release_date = "2023-07-19",
            title = "Barbie",
            video = false,
            vote_average = 7.279,
            vote_count = 5069
        ),
        Movie(
            adult = false,
            backdrop_path = "/1syW9SNna38rSl9fnXwc9fP7POW.jpg",
            genre_ids = listOf(28, 878, 12),
            id = 565770,
            original_language = "en",
            original_title = "Blue Beetle",
            overview = "Recent college grad Jaime Reyes returns home full of aspirations for his future, only to find that home is not quite as he left it. As he searches to find his purpose in the world, fate intervenes when Jaime unexpectedly finds himself in possession of an ancient relic of alien biotechnology: the Scarab.",
            popularity = 2868.214,
            poster_path = "/mXLOHHc1Zeuwsl4xYKjKh2280oL.jpg",
            release_date = "2023-08-16",
            title = "Blue Beetle",
            video = false,
            vote_average = 7.143,
            vote_count = 994
        )
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository =
            MovieRepositoryImpl(movieDao, apiService, movieDetailsDao, Dispatchers.Unconfined)
    }

    @Test
    fun shouldInsertMoviesInDbOnRefreshMovies() = runBlocking {
        `when`(apiService.fetchMovies()).thenReturn(
            Response.success(MovieResponse(1,results = testMovies,1 ,1)))

        repository.refreshMovies()

        Mockito.verify(movieDao, times(1)).insertMovies(testMovies)
    }

    @Test
    fun shouldReturnListFromDBWhenApiFails() = runBlocking {
        val listFromDB = flowOf(testMovies)
        `when`(apiService.fetchMovies()).thenReturn(Response.error(404, ResponseBody.create(null, "")))
        `when`(movieDao.getAllMovies()).thenReturn(listFromDB)

        val result = repository.refreshMovies()

        assert(listFromDB == result)
    }

    @Test
    fun shouldReturnDetailsFromDBWhenApiFails() = runBlocking {
        val movieId = 1
        val testLocalMovieDetail = createMockMovieDetailEntity()
        `when`(apiService.getMovieDetails(movieId)).thenReturn(Response.error(404, ResponseBody.create(null, "")))
        `when`(movieDetailsDao.getMovieDetail(movieId)).thenReturn(testLocalMovieDetail)

        val result = repository.getMovieDetail(movieId)

        assert(result == testLocalMovieDetail)
    }

    @Test
    fun shouldReturnFromLocalDBOnGetMovieDetails() = runBlocking {
        val movieId = 1
        val testLocalMovieDetail = createMockMovieDetailEntity()
        `when`(movieDetailsDao.getMovieDetail(movieId)).thenReturn(testLocalMovieDetail)

        val result = repository.getMovieDetail(movieId)

        assert(result == testLocalMovieDetail)
    }

    @Test
    fun shouldFetchFromServerWhenDetailsInLocalDBNotAvailable() = runBlocking {
        val movieId = 1
        val testRemoteMovieDetailResponse = movieDetailResponse
        `when`(movieDetailsDao.getMovieDetail(movieId)).thenReturn(null)
        `when`(apiService.getMovieDetails(movieId))
            .thenReturn(Response.success(testRemoteMovieDetailResponse))

        repository.getMovieDetail(movieId)

        Mockito.verify(movieDetailsDao)
            .insertMovieDetail(testRemoteMovieDetailResponse.toMovieDetailEntity())
    }

    private fun MovieDetailResponse.toMovieDetailEntity(): MovieDetailEntity {
        return MovieDetailEntity(
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
    }

    private fun createMockMovieDetailEntity(): MovieDetailEntity {
        return MovieDetailEntity(
            id = 1,
            title = "Test Movie",
            "",
            "",
            "",
            "",
            2,
            2,
            "",
            "",
            ""
        )
    }

    val movieDetailResponse = MovieDetailResponse(
        adult = false,
        backdrop_path = "/ctMserH8g2SeOAnCw5gFjdQF8mo.jpg",
        belongs_to_collection = null,
        budget = 145000000,
        genres = listOf(
            Genre(id = 35, name = "Comedy"),
            Genre(id = 12, name = "Adventure"),
            Genre(id = 14, name = "Fantasy")
        ),
        homepage = "https://www.barbie-themovie.com",
        id = 346698,
        imdb_id = "tt1517268",
        original_language = "en",
        original_title = "Barbie",
        overview = "Barbie and Ken are having the time of their lives in the colorful and seemingly perfect world of Barbie Land. However, when they get a chance to go to the real world, they soon discover the joys and perils of living among humans.",
        popularity = 1069.34,
        poster_path = "/iuFNMS8U5cb6xfzi51Dbkovj7vM.jpg",
        production_companies = listOf(
            ProductionCompany(
                id = 82968,
                logo_path = "/gRROMOG5bpF6TIDMbfaa5gnFFzl.png",
                name = "LuckyChap Entertainment",
                origin_country = "US"
            ),
            ProductionCompany(
                id = 437,
                logo_path = "/nu20mtwbEIhUNnQ5NXVhHsNknZj.png",
                name = "Heyday Films",
                origin_country = "GB"
            ),
            ProductionCompany(
                id = 181486,
                logo_path = null,
                name = "NB/GG Pictures",
                origin_country = "US"
            ),
            ProductionCompany(
                id = 6220,
                logo_path = "/cAj69EL1zSXmZH6STbMGZrunyMD.png",
                name = "Mattel",
                origin_country = "US"
            )
        ),
        production_countries = listOf(
            ProductionCountry(iso_3166_1 = "GB", name = "United Kingdom"),
            ProductionCountry(iso_3166_1 = "US", name = "United States of America")
        ),
        release_date = "2023-07-19",
        revenue = 1428545028,
        runtime = 114,
        spoken_languages = listOf(
            SpokenLanguage(
                english_name = "English",
                iso_639_1 = "en",
                name = "English"
            )
        ),
        status = "Released",
        tagline = "She's everything. He's just Ken.",
        title = "Barbie",
        video = false,
        vote_average = 7.279,
        vote_count = 5069
    )
}
