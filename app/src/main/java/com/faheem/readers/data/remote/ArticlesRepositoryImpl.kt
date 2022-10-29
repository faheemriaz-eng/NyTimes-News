package com.faheem.readers.data.remote

import com.faheem.readers.data.remote.base.BaseRepository

class ArticlesRepositoryImpl(private val service: ArticlesService) : ArticlesRepository,
    BaseRepository() {
    override suspend fun getMostViewedArticles(timePeriod: Int) =
        executeApi { service.fetchMostViewedArticles(period = timePeriod) }

}