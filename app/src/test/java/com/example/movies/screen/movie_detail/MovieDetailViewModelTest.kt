package com.example.movies.screen.movie_detail


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movies.core.resource.Resource
import com.example.movies.movie_detail.data.dto.local.MovieDetailEntity
import com.example.movies.movie_detail.domain.MovieDetailRepository
import com.example.movies.movie_detail.ui.TestMovieDetailViewModel
import com.example.movies.movie_list.data.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: MovieDetailRepository

    private lateinit var viewModel: TestMovieDetailViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = TestMovieDetailViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun shouldEmitDataOnFetchMovieDetails() = runTest {
        val movieId = 123
        val movieDetailResponse = createMockMovieDetailEntity() // Create a mock MovieDetailEntity
        Mockito.`when`(repository.getMovieDetail(movieId)).thenReturn(Resource.Success(movieDetailResponse.toDomain()))

        viewModel.fetchMovieDetail(movieId)

        val result = viewModel.movieDetail.first() // Observe the StateFlow
        assertNotNull(result)
        assertEquals(movieDetailResponse.toDomain(), result)
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
}
