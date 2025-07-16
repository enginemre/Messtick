package com.messtick.app.core.data.repository

import com.messtick.app.core.domain.repository.SilentMessageRepository
import com.messtick.app.notification.NotificationData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SilentMessageRepositoryImpl() : SilentMessageRepository {
    private val _silentMessages = MutableSharedFlow<NotificationData>(
        extraBufferCapacity = 1
    )

    override val silentMessages: Flow<NotificationData>
        get() = _silentMessages.asSharedFlow()

    override suspend fun sendSilentMessages(data: NotificationData) {
        _silentMessages.emit(data)
    }
}