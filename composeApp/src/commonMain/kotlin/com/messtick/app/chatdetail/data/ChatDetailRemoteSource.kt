package com.messtick.app.chatdetail.data

import com.messtick.app.chatdetail.data.dto.ChatDetailResponse
import com.messtick.app.chatdetail.data.dto.SendMessageResponse
import com.messtick.app.chatdetail.domain.ChatNotificationStatus
import com.messtick.app.core.data.networking.dto.StringResponse
import com.messtick.app.notification.NotificationType

interface ChatDetailRemoteSource {
   suspend fun getChatDetail(
        chatId: String,
        page: Int,
        pageSize: Int
    ): Result<ChatDetailResponse>

   suspend fun sendMessage(
        message: String,
        chatId: String
   ) : Result<SendMessageResponse>

   suspend fun readMessage(
       chatId: String
   ) : Result<StringResponse>

   suspend fun deleteChat(
       chatId: String
   ) :Result<StringResponse>

   suspend fun muteChat(
       chatId: String,
       notificationType: ChatNotificationStatus
   ) : Result<StringResponse>
}