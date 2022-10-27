package com.faheem.readers.data.remote

import com.faheem.readers.data.dtos.ArticlesDto
import com.faheem.readers.data.remote.base.NetworkResult

interface ArticlesRepository {
    suspend fun getMostViewedArticles(timePeriod: Int): NetworkResult<ArticlesDto>
}