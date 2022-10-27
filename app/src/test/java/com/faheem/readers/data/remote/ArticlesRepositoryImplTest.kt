package com.faheem.readers.data.remote

import com.faheem.readers.data.dtos.ArticlesDto
import com.faheem.readers.data.dtos.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesRepositoryImplTest {

    private var timePeriod: Int = 0

    lateinit var mockArticlesService: ArticlesService

    @Before
    fun setup() {
        mockArticlesService = mockk()
    }

    @Test
    fun `test fetch most viewed articles from service`() = runTest {
        // Given
        coEvery { mockArticlesService.fetchMostViewedArticles(timePeriod) } returns
                Response.success(mockk {
                    every { results } returns listOf(
                        Result(title = "Article 1"),
                        Result(title = "Article 2")
                    )
                })

        val expectedResult = NetworkResult.Success(mockk<ArticlesDto> {
            every { results } returns listOf(
                Result(title = "Article 1"),
                Result(title = "Article 2")
            )
        })

        // When
        val sut = ArticlesRepositoryImpl(mockArticlesService)
        val actualResult = sut.getMostViewedArticles(timePeriod) as NetworkResult.Success

        // Then
        Assert.assertEquals(expectedResult.data.results, actualResult.data.results)

        // Verify
        coVerify { mockArticlesService.fetchMostViewedArticles(timePeriod) }
    }

    @Test
    fun `test fetch most viewed articles from service is failed with error code 429`() = runTest {
        // Given
        val errorMessage = "Too many requests. You reached your per minute or per day rate limit"
        coEvery { mockArticlesService.fetchMostViewedArticles(timePeriod) } returns
                Response.error(429, errorMessage.toResponseBody("application/json".toMediaTypeOrNull()))


        // When
        val sut = ArticlesRepositoryImpl(mockArticlesService)
        val actualResult = sut.getMostViewedArticles(timePeriod) as NetworkResult.Error

        // Then
        Assert.assertEquals(
            "Too many requests. You reached your per minute or per day rate limit",
            actualResult.error.message
        )

        // Verify
        coVerify { mockArticlesService.fetchMostViewedArticles(timePeriod) }
    }
}