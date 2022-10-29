package com.faheem.readers.domain

sealed interface DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>
    data class Loading(val isLoading: Boolean) : DataState<Nothing>
    data class Error(val error: String) : DataState<Nothing>
}