package com.faheem.readers.data.remote

import com.faheem.readers.data.remote.base.BaseRepository
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(private val service: ArticlesService) : ArticlesRepository,
    BaseRepository() {
    override suspend fun getMostViewedArticles(timePeriod: Int) =
        executeApi { service.fetchMostViewedArticles(period = timePeriod) }

}