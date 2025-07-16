package com.messtick.app.chatdetail.domain.usecase

import com.messtick.app.core.data.networking.Resource
import kotlinx.coroutines.flow.Flow

interface DeleteChatUseCase{
    operator fun invoke(chatId: String) : Flow<Resource<Boolean>>
}