package com.faheem.readers.data.remote

import com.faheem.readers.data.dtos.ArticlesDto
import com.faheem.readers.data.dtos.Result
import com.faheem.readers.data.remote.base.NetworkResult
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesRepositoryImplTest {

    private var timePeriod: Int = 0

    var mockArticlesService: ArticlesService = mockk()

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
        Assertions.assertEquals(expectedResult.data.results, actualResult.data.results)

        // Verify
        coVerify { mockArticlesService.fetchMostViewedArticles(timePeriod) }
    }

    @TestFactory
    fun `test fetch most viewed articles from service is failed`(): Collection<DynamicTest> {

        return listOf(
            401 to "The requested resource requires user authentication",
            403 to "You don't have access to this information",
            404 to "The server has not found anything matching the Request-URI",
            408 to "Please check your internet connection",
            429 to "You reached your per minute or per day rate limit",
            500 to "This request unfortunately failed please try again",
            502 to "Bad Gateway"
        ).map { (responseCode, expected) ->
            dynamicTest(
                "when api is failed with response code \"$responseCode\", " +
                        "then it should return message $expected"
            ) {
                // Given
                coEvery { mockArticlesService.fetchMostViewedArticles(timePeriod) } returns
                        Response.error(
                            responseCode,
                            "Something went wrong".toResponseBody("application/json".toMediaTypeOrNull())
                        )

                // When
                val sut = ArticlesRepositoryImpl(mockArticlesService)
                runTest {
                    val actual = sut.getMostViewedArticles(timePeriod) as NetworkResult.Error

                    // Then
                    Assertions.assertEquals(expected, actual.error.message)
                }

                // Verify
                coVerify { mockArticlesService.fetchMostViewedArticles(timePeriod) }
            }
        }
    }
}