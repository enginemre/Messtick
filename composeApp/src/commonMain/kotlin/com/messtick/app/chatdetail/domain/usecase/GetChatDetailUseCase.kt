package com.messtick.app.chatdetail.domain.usecase

import androidx.paging.PagingData
import com.messtick.app.chatdetail.domain.ChatDetailModel
import kotlinx.coroutines.flow.Flow

interface GetChatDetailUseCase {
    operator fun invoke(
        chatId: String
    ): Flow<PagingData<ChatDetailModel>>
}