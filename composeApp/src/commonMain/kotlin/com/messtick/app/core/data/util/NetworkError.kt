package com.messtick.app.core.data.util

sealed class NetworkError: Exception() {
    data object RequestTimeOut : NetworkError()
    data object TooManyRequests : NetworkError()
    data object NoInternet : NetworkError()
    data object ServerError : NetworkError()
    data object Serialization : NetworkError()
    data object Unknown : NetworkError()
}