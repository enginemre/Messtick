package com.messtick.app.home.data

import com.messtick.app.core.data.networking.safeCall
import com.messtick.app.home.data.dto.ChatItemDto
import com.messtick.app.home.data.dto.GetChatResponse
import com.messtick.app.home.domain.model.ChatItem
import io.ktor.client.*
import io.ktor.client.request.*

class HomeRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : HomeRemoteDataSource {
    override suspend fun getChatList(): Result<GetChatResponse> = safeCall {
        httpClient.get {
            url("/api/chat")
        }
    }

}