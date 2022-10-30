package com.faheem.readers.ui.home

import com.faheem.readers.domain.DataState
import com.faheem.readers.domain.models.Article
import com.faheem.readers.domain.usecases.GetArticlesUseCase
import com.faheem.readers.testutils.InstantExecutorExtension
import com.faheem.readers.testutils.getOrAwaitValue
import com.faheem.readers.ui.home.master.HomeUiState
import com.faheem.readers.ui.home.master.HomeViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@DelicateCoroutinesApi
@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class)
internal class HomeViewModelTest {


    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @BeforeEach
    fun setUp() = runTest {
        Dispatchers.setMain(mainThreadSurrogate)
    }


    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `test get articles should return success from use-case`() = runTest {
        // Given
        val mockData = listOf(Article("Title 1", "By Author", "20-10-2022"))

        val mockUseCase = mockk<GetArticlesUseCase> {
            every { fetchArticles(7) } returns flowOf(DataState.Success(mockData))
        }

        // When
        val sut = HomeViewModel(mockUseCase)
        sut.getArticles(7)
        val actual = sut.uiState.getOrAwaitValue()
        val expected = HomeUiState.Success(listOf(Article("Title 1", "By Author", "20-10-2022")))

        // Then
        Assertions.assertEquals(expected, actual)

        // Verify
        verify { mockUseCase.fetchArticles(7) }
    }

    @Test
    fun `test get articles is failed should return error from use-case `() = runTest {
        // Given
        val mockUseCase = mockk<GetArticlesUseCase> {
            every { fetchArticles(7) } returns flowOf(DataState.Error("Something went wrong"))
        }

        // When
        val sut = HomeViewModel(mockUseCase)
        sut.getArticles(7)
        val actual = sut.uiState.getOrAwaitValue()
        val expected = HomeUiState.Error("Something went wrong")

        // Then
        Assertions.assertEquals(expected, actual)

        // Verify
        verify { mockUseCase.fetchArticles(7) }
    }

    @Test
    fun `test get articles started, should return is loading ui state with true`() = runTest {
        // Given
        val mockUseCase = mockk<GetArticlesUseCase> {
            every { fetchArticles(7) } returns flowOf(DataState.Loading(true))
        }

        // When
        val sut = HomeViewModel(mockUseCase)
        sut.getArticles(7)
        val actual = sut.uiState.getOrAwaitValue()
        val expected = HomeUiState.Loading(true)

        // Then
        Assertions.assertEquals(expected, actual)

        // Verify
        verify {
            mockUseCase.fetchArticles(7)
        }
    }


    @Test
    fun `test get articles fetched, should return is loading ui state with false`() = runTest {
        // Given
        val mockUseCase = mockk<GetArticlesUseCase> {
            every { fetchArticles(7) } returns flowOf(DataState.Loading(false))
        }

        // When
        val sut = HomeViewModel(mockUseCase)
        sut.getArticles(7)
        val actual = sut.uiState.getOrAwaitValue()
        val expected = HomeUiState.Loading(false)

        // Then
        Assertions.assertEquals(expected, actual)

        // Verify
        verify {
            mockUseCase.fetchArticles(7)
        }
    }
}