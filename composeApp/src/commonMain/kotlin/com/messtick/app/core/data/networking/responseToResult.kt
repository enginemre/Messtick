package com.messtick.app.core.data.networking

import com.messtick.app.core.data.util.NetworkError
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Result.success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.failure(NetworkError.Serialization)
            }
        }
        408 -> Result.failure(NetworkError.RequestTimeOut)
        429 -> Result.failure(NetworkError.TooManyRequests)
        in 500..599 -> Result.failure(NetworkError.ServerError)
        else -> Result.failure(NetworkError.Unknown)
    }
}