package com.messtick.app.home.data

import com.messtick.app.core.data.networking.DispatcherProvider
import com.messtick.app.core.data.networking.NetworkConfig
import com.messtick.app.core.data.networking.Resource
import com.messtick.app.core.data.networking.asResource
import com.messtick.app.home.domain.model.ChatItem
import com.messtick.app.home.domain.usecase.GetChatsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetChatUseCaseImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val homeRemoteDataSource: HomeRemoteDataSource
) : GetChatsUseCase {
    override fun invoke(): Flow<Resource<List<ChatItem>>> = flow {
        val result = homeRemoteDataSource.getChatList().getOrThrow()
        emit(result.data?.map {
            ChatItem(
                guid = it.guid,
                name = it.name.orEmpty(),
                lastMessage = it.message.orEmpty(),
                unread = it.unread,
                imageUrl = "${NetworkConfig.IMAGE_URL}/${it.guid}"
            )
        }.orEmpty())
    }.flowOn(dispatcherProvider.io).asResource()
}