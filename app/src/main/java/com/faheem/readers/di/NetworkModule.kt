package com.faheem.readers.di

import com.faheem.readers.data.client.ApiClient
import com.faheem.readers.data.remote.ArticlesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient = ApiClient()

    @Provides
    @Singleton
    fun provideRetrofit(apiClient: ApiClient): Retrofit {
        return apiClient.build("https://api.nytimes.com/svc/mostpopular/v2/")
    }

    @Singleton
    @Provides
    fun providesArticlesService(retrofit: Retrofit): ArticlesService =
        retrofit.create(ArticlesService::class.java)
}