package com.faheem.readers.data.remote.base

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val error: Exception) : NetworkResult<Nothing>()
}