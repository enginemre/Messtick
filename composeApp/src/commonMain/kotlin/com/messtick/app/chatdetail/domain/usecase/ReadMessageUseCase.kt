package com.messtick.app.chatdetail.domain.usecase

import com.messtick.app.core.data.networking.Resource
import kotlinx.coroutines.flow.Flow

interface ReadMessageUseCase {
    operator fun invoke(guid: String): Flow<Resource<Boolean>>
}