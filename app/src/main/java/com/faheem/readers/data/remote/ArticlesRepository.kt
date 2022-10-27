package com.faheem.readers.data.remote

import com.faheem.readers.data.dtos.ArticlesDto

interface ArticlesRepository {
    suspend fun getMostViewedArticles(timePeriod: Int): NetworkResult<ArticlesDto>
}