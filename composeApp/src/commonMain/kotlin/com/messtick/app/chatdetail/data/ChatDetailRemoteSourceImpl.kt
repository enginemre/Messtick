package com.messtick.app.chatdetail.data

import com.messtick.app.chatdetail.data.dto.ChatDetailResponse
import com.messtick.app.chatdetail.data.dto.SendMessageResponse
import com.messtick.app.chatdetail.domain.ChatNotificationStatus
import com.messtick.app.core.data.networking.dto.StringResponse
import com.messtick.app.core.data.networking.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class ChatDetailRemoteSourceImpl(
    private val httpClient: HttpClient,
) : ChatDetailRemoteSource {
    override suspend fun getChatDetail(
        chatId: String,
        page: Int,
        pageSize: Int
    ): Result<ChatDetailResponse> = safeCall {
        httpClient.get {
            url("/api/message")
            parameter("guid", chatId)
            parameter("pageSize", pageSize)
            parameter("pageNumber", page)
        }
    }

    override suspend fun sendMessage(message: String, chatId: String): Result<SendMessageResponse> =
        safeCall {
            httpClient.post {
                url("/api/message")
                contentType(ContentType.Application.Json)
                setBody(
                    buildJsonObject {
                        put("message", message)
                        put("chatGuid", chatId)
                    }
                )
            }
        }

    override suspend fun readMessage(chatId: String): Result<StringResponse> = safeCall {
        val result =  httpClient.put {
            url("/api/chat/$chatId/readed")
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun deleteChat(chatId: String): Result<StringResponse> = safeCall {
        httpClient.delete {
            url("/api/chat/$chatId")
        }
    }

    override suspend fun muteChat(
        chatId: String,
        notificationType: ChatNotificationStatus
    ): Result<StringResponse> = safeCall {
        httpClient.put {
            url("/api/chat/$chatId/notifications/${notificationType.ordinal}")
            contentType(ContentType.Application.Json)
            setBody(
                buildJsonObject {
                    put("guid", chatId)
                    put("type", notificationType.ordinal)
                }
            )
        }
    }
}