package com.messtick.app.home.domain.usecase

import com.messtick.app.home.domain.model.ChatItem
import kotlinx.coroutines.flow.Flow
import com.messtick.app.core.data.networking.Resource

interface GetChatsUseCase {
    operator fun invoke(): Flow<Resource<List<ChatItem>>>
}