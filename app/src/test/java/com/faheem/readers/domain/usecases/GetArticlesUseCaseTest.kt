package com.faheem.readers.domain.usecases

import com.faheem.readers.data.dtos.ArticlesDto
import com.faheem.readers.data.remote.ArticlesRepository
import com.faheem.readers.data.remote.base.NetworkResult
import com.faheem.readers.domain.DataState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetArticlesUseCaseTest {

    @Test
    fun `test fetch articles should return domain model`() = runTest {

        // Given

        val mockRepository = mockk<ArticlesRepository>()
        val mockData = mockk<ArticlesDto> {
            every { results } returns listOf(
                com.faheem.readers.data.dtos.Result(
                    title = "title1",
                    byline = "by author",
                    publishedDate = "2022-10-17"
                )
            )
        }

        coEvery { mockRepository.getMostViewedArticles(7) } returns NetworkResult.Success(mockData)

        // When

        val sut = GetArticlesUseCase(mockRepository)
        val actual = sut.fetchArticles(7).first() as DataState.Success

        // Then

        Assertions.assertEquals("title1", actual.data.first().title)
        Assertions.assertEquals("by author", actual.data.first().by)
        Assertions.assertEquals("2022-10-17", actual.data.first().publishDate)

        // Verify
        coVerify { mockRepository.getMostViewedArticles(7) }
    }

    @Test
    fun `test fetch articles is failed should return error message`() = runTest {

        // Given

        val mockRepository = mockk<ArticlesRepository>()
        val mockData = mockk<Exception> {
            every { message } returns "Something went wrong"
        }

        coEvery { mockRepository.getMostViewedArticles(7) } returns NetworkResult.Error(mockData)

        // When

        val sut = GetArticlesUseCase(mockRepository)
        val actual = sut.fetchArticles(7).first() as DataState.Error

        // Then
        Assertions.assertEquals("Something went wrong", actual.error)

        // Verify
        coVerify { mockRepository.getMostViewedArticles(7) }
    }
}