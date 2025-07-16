package com.messtick.app.chatdetail.data.usecase

import com.messtick.app.chatdetail.data.ChatDetailRemoteSource
import com.messtick.app.chatdetail.domain.usecase.DeleteChatUseCase
import com.messtick.app.core.data.networking.DispatcherProvider
import com.messtick.app.core.data.networking.Resource
import com.messtick.app.core.data.networking.asResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteChatUseCaseImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val chatDetailRemoteSource: ChatDetailRemoteSource
) : DeleteChatUseCase {
    override fun invoke(chatId: String): Flow<Resource<Boolean>> = flow {
        val result = chatDetailRemoteSource.deleteChat(chatId).getOrNull()
        emit(result?.isSuccess ?: false)
    }.flowOn(dispatcherProvider.io).asResource()
}