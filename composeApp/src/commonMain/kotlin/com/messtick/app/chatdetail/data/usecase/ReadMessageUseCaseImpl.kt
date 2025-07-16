package com.messtick.app.chatdetail.data.usecase

import com.messtick.app.chatdetail.data.ChatDetailRemoteSource
import com.messtick.app.chatdetail.domain.usecase.ReadMessageUseCase
import com.messtick.app.core.data.networking.DispatcherProvider
import com.messtick.app.core.data.networking.Resource
import com.messtick.app.core.data.networking.asResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ReadMessageUseCaseImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val chatDetailRemoteSource: ChatDetailRemoteSource
) : ReadMessageUseCase {
    override fun invoke(guid: String): Flow<Resource<Boolean>> = flow{
        val result = chatDetailRemoteSource.readMessage(guid).getOrNull()
        emit(result?.isSuccess ?: false)
    }.flowOn(dispatcherProvider.io).asResource()
}