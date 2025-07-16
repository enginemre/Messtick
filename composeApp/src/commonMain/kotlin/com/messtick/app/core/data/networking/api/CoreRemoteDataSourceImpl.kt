package com.messtick.app.core.data.networking.api

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class CoreRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : CoreRemoteDataSource {
    override suspend fun registerDeviceId(pushToken: String, deviceId: String) {
        httpClient.post {
            url(CoreRemoteDataSource.REGISTER_DEVICE)
            contentType(ContentType.Application.Json)
            setBody(
                buildJsonObject {
                    put("deviceId", pushToken)
                    put("uniqId", deviceId)
                }
            )
        }
    }


}