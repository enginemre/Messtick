package com.messtick.app.chatdetail.domain.usecase

import com.messtick.app.chatdetail.domain.ChatDetailModel
import com.messtick.app.core.data.networking.Resource
import kotlinx.coroutines.flow.Flow

interface SendMessageUseCase {
    operator fun invoke(message: String, chatId: String,chatOwnerId : String): Flow<Resource<ChatDetailModel?>>
}