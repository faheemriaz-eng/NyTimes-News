package com.faheem.readers.data.remote

import com.faheem.readers.data.dtos.ArticlesDto

class ArticlesRepositoryImpl(private val service: ArticlesService) : ArticlesRepository {
    override suspend fun getMostViewedArticles(timePeriod: Int): NetworkResult<ArticlesDto> {
        val response = service.fetchMostViewedArticles(period = timePeriod)
        if (response.isSuccessful) {
            return NetworkResult.Success(response.body()!!)
        }

        return NetworkResult.Error(Exception(response.errorBody()?.string()))
    }
}