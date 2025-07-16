package com.messtick.app.notification

import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration


actual fun onApplicationStartPlatformSpecific() {
    NotifierManager.initialize(
        configuration = NotificationPlatformConfiguration.Android(
            notificationIconResId = com.messtick.app.R.mipmap.ic_launcher,
            showPushNotification = true,
        )
    )
}

