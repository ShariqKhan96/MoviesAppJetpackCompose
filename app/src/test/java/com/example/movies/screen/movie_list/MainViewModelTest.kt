package com.example.movies.screen.movie_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movies.Movie
import com.example.movies.repository.MovieRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var repository: MovieRepository

    private lateinit var viewModel: MainViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = MainViewModel(repository, testDispatcher)
    }

    @Test
    fun shouldFilterListOnBasisOfSearchQuery()  = runBlocking  {
        val movies = createFakeMovieList(5)
        movies[0].title = "testSample"
        movies[1].title = "testSample2"
        val searchQuery = "test"
        `when`(repository.refreshMovies()).thenReturn(flowOf(movies))

        viewModel.updateSearchQuery(searchQuery)
        viewModel.refreshData()

        assert(viewModel.filteredMovies.first().size == 2)
        assertEquals(viewModel.movieList.first(), movies)
        assertEquals(viewModel.movieList.first().size, movies.size)
    }

    @Test
    fun `refreshData should update movieList with data from repository`() = testDispatcher.runBlockingTest {
        val testMovieList = createFakeMovieList(5)
        `when`(repository.refreshMovies()).thenReturn( flowOf(testMovieList))

        viewModel.refreshData()

        assertEquals(viewModel.movieList.first(), testMovieList)
        assertEquals(viewModel.movieList.first().size, testMovieList.size)
        assertEquals(viewModel.filteredMovies.first(), testMovieList)
        assertEquals(viewModel.filteredMovies.first().size, testMovieList.size)
    }

    private fun createFakeMovie(): Movie {
        return Movie(
            adult = false,
            backdrop_path = "/fake_backdrop.jpg",
            genre_ids = listOf(1, 2, 3),
            id = 1,
            original_language = "en",
            original_title = "Fake Movie",
            overview = "This is a fake movie overview.",
            popularity = 7.8,
            poster_path = "/fake_poster.jpg",
            release_date = "2023-09-30",
            title = "Fake Movie",
            video = false,
            vote_average = 7.5,
            vote_count = 100
        )
    }

    private fun createFakeMovieList(size: Int): List<Movie> {
        val fakeMovieList = mutableListOf<Movie>()
        repeat(size) {
            fakeMovieList.add(createFakeMovie())
        }
        return fakeMovieList
    }

}
