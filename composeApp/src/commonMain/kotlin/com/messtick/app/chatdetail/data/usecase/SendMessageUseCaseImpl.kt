package com.messtick.app.chatdetail.data.usecase

import com.messtick.app.chatdetail.data.ChatDetailRemoteSource
import com.messtick.app.chatdetail.domain.ChatDetailModel
import com.messtick.app.chatdetail.domain.usecase.SendMessageUseCase
import com.messtick.app.core.data.networking.DispatcherProvider
import com.messtick.app.core.data.networking.Resource
import com.messtick.app.core.data.networking.asResource
import com.messtick.app.core.util.toInstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SendMessageUseCaseImpl(
    private val chatDetailRemoteSource: ChatDetailRemoteSource,
    private val dispatcherProvider: DispatcherProvider
) : SendMessageUseCase {
    override fun invoke(message: String, chatId: String,chatOwnerId : String): Flow<Resource<ChatDetailModel?>> = flow {
        val result = chatDetailRemoteSource.sendMessage(message, chatId).getOrThrow().data
        val mappedResult =   ChatDetailModel(
            message = result?.message.orEmpty(),
            date = result?.createDate?.toInstant(),
            guid = result?.guid.orEmpty(),
            messageOwnerName = result?.user?.name,
            messageOwnerId = result?.user?.guid.orEmpty(),
            chatOwnerId = chatOwnerId
        )
        emit(mappedResult)
    }.flowOn(dispatcherProvider.io).asResource()
}