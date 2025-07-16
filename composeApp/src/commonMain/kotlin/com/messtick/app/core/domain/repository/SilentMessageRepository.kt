package com.messtick.app.core.domain.repository

import com.messtick.app.notification.NotificationData
import kotlinx.coroutines.flow.Flow

interface SilentMessageRepository {
    val silentMessages: Flow<NotificationData>
    suspend fun sendSilentMessages(data: NotificationData)
}