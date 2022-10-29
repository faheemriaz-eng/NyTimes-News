package com.faheem.readers.domain.usecases

import com.faheem.readers.data.dtos.asDomain
import com.faheem.readers.data.remote.ArticlesRepository
import com.faheem.readers.data.remote.base.NetworkResult
import com.faheem.readers.domain.DataState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class GetArticlesUseCase(private val repository: ArticlesRepository) {

    fun fetchArticles(timePeriod: Int) = flow {
        val result = when (val response = repository.getMostViewedArticles(timePeriod)) {
            is NetworkResult.Error -> DataState.Error(
                response.error.message ?: "Something went wrong"
            )
            is NetworkResult.Success -> DataState.Success(response.data.asDomain())
        }
        emit(result)
    }.onStart { emit(DataState.Loading(true))  }
        .onCompletion { emit(DataState.Loading(false)) }
        .catch { ex ->
            emit(DataState.Loading(false))
            DataState.Error(ex.message ?: "Something went wrong")
        }

}