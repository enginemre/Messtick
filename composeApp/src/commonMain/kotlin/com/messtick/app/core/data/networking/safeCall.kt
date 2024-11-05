package com.messtick.app.core.data.networking

import com.messtick.app.core.data.util.NetworkError
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T> {
    val response = try {
        execute()
    } catch(e: UnresolvedAddressException) {
        return Result.failure(NetworkError.NoInternet)
    } catch(e: SerializationException) {
        return Result.failure(NetworkError.Serialization)
    } catch(e: Exception) {
        coroutineContext.ensureActive()
        e.printStackTrace()
        return Result.failure(NetworkError.Unknown)
    }

    return responseToResult(response)
}