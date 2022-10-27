package com.faheem.readers.data.remote.base

import retrofit2.Response

abstract class BaseRepository {

    suspend fun <T> executeApi(call: suspend () -> Response<T>): NetworkResult<T> {
        try {

            val response = call.invoke()
            if (response.isSuccessful)
                return NetworkResult.Success(response.body()!!)

            return NetworkResult.Error(Exception(getCustomErrorMessage(response)))

        } catch (exception: Exception) {
            return NetworkResult.Error(exception)
        }
    }

    private fun <T> getCustomErrorMessage(response: Response<T>): String {
        return when (response.code()) {
            401 -> getMessage(NetworkErrors.Unauthorized)
            403 -> getMessage(NetworkErrors.Forbidden)
            404 -> getMessage(NetworkErrors.NotFound)
            408 -> getMessage(NetworkErrors.RequestTimedOut)
            429 -> getMessage(NetworkErrors.TooManyRequests)
            502 -> getMessage(NetworkErrors.BadGateway)
            else -> getMessage(NetworkErrors.UnknownError())
        }
    }

    private fun getMessage(error: NetworkErrors): String {
        return when (error) {
            NetworkErrors.Unauthorized -> "The requested resource requires user authentication"
            is NetworkErrors.NoInternet, NetworkErrors.RequestTimedOut -> "Please check your internet connection"
            is NetworkErrors.BadGateway -> "Bad Gateway"
            is NetworkErrors.NotFound -> "The server has not found anything matching the Request-URI"
            is NetworkErrors.Forbidden -> "You don't have access to this information"
            is NetworkErrors.TooManyRequests -> "You reached your per minute or per day rate limit"
            is NetworkErrors.UnknownError -> "This request unfortunately failed please try again"
        }
    }

    sealed class NetworkErrors {
        object Unauthorized : NetworkErrors()
        object NoInternet : NetworkErrors()
        object RequestTimedOut : NetworkErrors()
        object BadGateway : NetworkErrors()
        object NotFound : NetworkErrors()
        object Forbidden : NetworkErrors()
        object TooManyRequests : NetworkErrors()
        open class UnknownError : NetworkErrors()
    }
}