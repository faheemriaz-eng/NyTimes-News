package com.faheem.readers.data.remote

import com.faheem.readers.data.dtos.ArticlesDto

class ArticlesRepositoryImpl(private val service: ArticlesService) : ArticlesRepository {
    override suspend fun getMostViewedArticles(timePeriod: Int): ArticlesDto {
        val response = service.fetchMostViewedArticles(timePeriod)
        if (response.isSuccessful) {
            return response.body()!!
        }

        return ArticlesDto(emptyList())
    }
}