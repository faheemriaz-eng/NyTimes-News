package com.faheem.readers.data.remote

import com.faheem.readers.data.dtos.ArticlesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticlesService {
    @GET("viewed/{period}.json")
    suspend fun fetchMostViewedArticles(@Path("period") period: Int): Response<ArticlesDto>
}