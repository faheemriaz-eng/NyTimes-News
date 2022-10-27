package com.faheem.readers.data.remote

import com.faheem.readers.data.dtos.ArticlesDto
import com.faheem.readers.data.remote.base.BaseRepository
import com.faheem.readers.data.remote.base.NetworkResult

class ArticlesRepositoryImpl(private val service: ArticlesService) : ArticlesRepository,
    BaseRepository() {
    override suspend fun getMostViewedArticles(timePeriod: Int): NetworkResult<ArticlesDto> =
        executeApi { service.fetchMostViewedArticles(period = timePeriod) }

}