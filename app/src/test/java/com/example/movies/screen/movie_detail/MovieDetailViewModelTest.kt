package com.example.movies.screen.movie_detail


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movies.core.resource.Resource
import com.example.movies.movie_detail.data.dto.local.MovieDetailEntity
import com.example.movies.movie_detail.domain.MovieDetailRepository
import com.example.movies.movie_detail.domain.use_case.GetMovieDetailByIdUseCase
import com.example.movies.movie_detail.ui.MovieDetailViewModel
import com.example.movies.movie_list.data.toDomain
import com.example.movies.movie_list.domain.repository.MovieRepository
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

    private lateinit var viewModel: MovieDetailViewModel

    private lateinit var getMovieDetailByIdUseCase: GetMovieDetailByIdUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getMovieDetailByIdUseCase = GetMovieDetailByIdUseCase(repository)
        viewModel = MovieDetailViewModel(getMovieDetailByIdUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun shouldEmitDataOnFetchMovieDetails() = runTest {
        val movieId = 123
        val movieDetailResponse = createMockMovieDetailEntity().toDomain() // Create a mock MovieDetailEntity
        Mockito.`when`(repository.getMovieDetail(movieId)).thenReturn(Resource.Success(movieDetailResponse))

        viewModel.fetchMovieDetail(movieId)

        val result = viewModel.movieDetailState.value.data // Observe the StateFlow
        assertNotNull(result)
        assertEquals(movieDetailResponse, result)
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
