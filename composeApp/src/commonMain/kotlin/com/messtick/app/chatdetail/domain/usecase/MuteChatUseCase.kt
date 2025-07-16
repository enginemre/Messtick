package com.messtick.app.chatdetail.domain.usecase

import com.messtick.app.chatdetail.domain.ChatNotificationStatus
import com.messtick.app.core.data.networking.Resource
import kotlinx.coroutines.flow.Flow

interface MuteChatUseCase {
    operator fun invoke(
        chatId: String,
        notificationType: ChatNotificationStatus
    ): Flow<Resource<Boolean>>
}