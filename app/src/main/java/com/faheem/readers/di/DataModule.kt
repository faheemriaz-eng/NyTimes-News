package com.faheem.readers.di

import com.faheem.readers.data.remote.ArticlesRepository
import com.faheem.readers.data.remote.ArticlesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun provideArticlesRepository(repository: ArticlesRepositoryImpl): ArticlesRepository


}