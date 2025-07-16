package com.messtick.app.home.data

import com.messtick.app.home.data.dto.ChatItemDto
import com.messtick.app.home.data.dto.GetChatResponse
import com.messtick.app.home.domain.model.ChatItem

interface HomeRemoteDataSource {
    suspend fun getChatList(): Result<GetChatResponse>
}