package com.faheem.readers.domain.usecases

import com.faheem.readers.data.dtos.ArticlesDto
import com.faheem.readers.data.remote.ArticlesRepository
import com.faheem.readers.data.remote.base.NetworkResult
import com.faheem.readers.domain.DataState
import com.faheem.readers.domain.models.Article
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetArticlesUseCaseTest {

    @Test
    fun `test fetch articles should return success`() = runTest {
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
        val actual = sut.fetchArticles(7)
        val expected = DataState.Success(
            listOf(
                Article(
                    title = "title1",
                    by = "by author",
                    publishDate = "2022-10-17",
                    description = "", source = "", imageUrl = "", largeImageUrl = ""
                )
            )
        )

        // Then
        Assertions.assertEquals(3, actual.count())
        Assertions.assertEquals(DataState.Loading(true), actual.first())
        Assertions.assertEquals(DataState.Loading(false), actual.last())
        Assertions.assertEquals(expected, actual.drop(1).first())

        // Verify
        coVerify { mockRepository.getMostViewedArticles(7) }
    }

    @Test
    fun `test fetch articles is failed should return error`() = runTest {

        // Given
        val mockRepository = mockk<ArticlesRepository>()
        val mockData = mockk<Exception> {
            every { message } returns "Something went wrong"
        }

        coEvery { mockRepository.getMostViewedArticles(7) } returns NetworkResult.Error(mockData)

        // When
        val sut = GetArticlesUseCase(mockRepository)
        val actual = sut.fetchArticles(7)
        val expected = DataState.Error("Something went wrong")

        // Then
        Assertions.assertEquals(3, actual.count())
        Assertions.assertEquals(DataState.Loading(true), actual.first())
        Assertions.assertEquals(DataState.Loading(false), actual.last())
        Assertions.assertEquals(expected, actual.drop(1).first())

        // Verify
        coVerify { mockRepository.getMostViewedArticles(7) }
    }
}