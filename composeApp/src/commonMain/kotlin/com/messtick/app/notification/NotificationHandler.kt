package com.messtick.app.notification


import com.mmk.kmpnotifier.notification.Notifier
import com.mmk.kmpnotifier.notification.NotifierManager

data class NotificationData(
    val type: NotificationType,
    val title: String,
    val message: String,
    val data: String?,
)

enum class NotificationType {
    Normal,
    Silent,
    Messages
}


object NotificationHandler {

    fun mapToNotificationData(map: Map<String, *>): Result<NotificationData> {
        return kotlin.runCatching {
            val type = checkNotNull(map["type"] as? String)
            val title = checkNotNull(map["title"] as? String)
            val message = checkNotNull(map["message"] as? String)
            val data = map["data"] as? String
            NotificationData(
                type = NotificationType.entries[type.toIntOrNull() ?: 0],
                title = title,
                message = message,
                data = data
            )
        }
    }

    fun handleNotification(notificationData: NotificationData) {
        when (notificationData.type) {
            NotificationType.Silent -> {

            }

            NotificationType.Normal -> {
                NotifierManager.getLocalNotifier().notify(
                    title = notificationData.title,
                    body = notificationData.message,
                    payloadData = if (notificationData.data == null) emptyMap() else mapOf(
                        Notifier.KEY_URL to notificationData.data,
                    )
                )
            }

            NotificationType.Messages -> {

            }
        }
    }
}
