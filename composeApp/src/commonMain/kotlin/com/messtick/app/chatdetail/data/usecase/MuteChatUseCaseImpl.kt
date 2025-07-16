package com.messtick.app.chatdetail.data.usecase

import com.messtick.app.chatdetail.data.ChatDetailRemoteSource
import com.messtick.app.chatdetail.domain.ChatNotificationStatus
import com.messtick.app.chatdetail.domain.usecase.MuteChatUseCase
import com.messtick.app.core.data.networking.DispatcherProvider
import com.messtick.app.core.data.networking.Resource
import com.messtick.app.core.data.networking.asResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MuteChatUseCaseImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val chatDetailRemoteSource: ChatDetailRemoteSource,
) : MuteChatUseCase {
    override fun invoke(
        chatId: String,
        notificationType: ChatNotificationStatus
    ): Flow<Resource<Boolean>> = flow {
        val result = chatDetailRemoteSource.muteChat(chatId, notificationType).getOrNull()
        emit(result?.isSuccess ?: false)
    }.flowOn(dispatcherProvider.io).asResource()
}