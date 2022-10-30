package com.faheem.readers.ui.features.home.detail

import com.faheem.readers.domain.models.Article
import com.faheem.readers.testutils.InstantExecutorExtension
import com.faheem.readers.testutils.getOrAwaitValue
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class ArticleDetailsViewModelTest {

    @Test
    fun `test on data received from master`() {
        // Given
        val mockData = Article(title = "Title 1", by = "By Author", publishDate = "20-10-2022")

        // When
        val sut = ArticleDetailsViewModel()
        sut.initBundleData(mockData)
        val expected = Article(title = "Title 1", by = "By Author", publishDate = "20-10-2022")

        // Then

        Assertions.assertEquals(expected, sut.article.getOrAwaitValue())
    }

}