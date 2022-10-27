package com.faheem.readers.data.remote

import com.faheem.readers.data.dtos.ArticlesDto
import com.faheem.readers.data.dtos.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesRepositoryImplTest {

    private var timePeriod: Int = 0

    @Test
    fun `test fetch most viewed articles from service`() = runTest {
        // Given
        val mockArticlesService = mockk<ArticlesService>()
        coEvery { mockArticlesService.fetchMostViewedArticles(timePeriod) } returns
                Response.success(mockk {
                    every { results } returns listOf(
                        Result(title = "Article 1"),
                        Result(title = "Article 2")
                    )
                })

        val expectedResult = mockk<ArticlesDto> {
            every { results } returns listOf(
                Result(title = "Article 1"),
                Result(title = "Article 2")
            )
        }

        // When
        val sut = ArticlesRepositoryImpl(mockArticlesService)
        val actualResult = sut.getMostViewedArticles(timePeriod)

        // Then
        Assert.assertEquals(expectedResult.results, actualResult.results)

        // Verify
        coVerify { mockArticlesService.fetchMostViewedArticles(timePeriod) }
    }
}