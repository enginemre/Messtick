package com.messtick.app.notification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.messtick.app.core.domain.RegisterDeviceUseCase
import com.messtick.app.core.domain.repository.SilentMessageRepository
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import org.koin.compose.getKoin


@Composable
fun NotificationInitializer() {
    val silentMessageRepository = getKoin().get<SilentMessageRepository>()
    val registerDeviceUseCase = getKoin().get<RegisterDeviceUseCase>()
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        onApplicationStartPlatformSpecific()
        NotifierManager.getPermissionUtil().askNotificationPermission()
        NotifierManager.addListener(object : NotifierManager.Listener {
            override fun onNewToken(token: String) {
                super.onNewToken(token)
                Napier.d("Push Token: $token")
                registerDeviceUseCase(token).launchIn(scope)
            }

            override fun onNotificationClicked(data: PayloadData) {
                super.onNotificationClicked(data)
                Napier.d("Push Data: $data")
            }

            override fun onPayloadData(data: PayloadData) {
                Napier.d("Push Notification payloadData: $data")
                val notificationData = NotificationHandler.mapToNotificationData(data)
                notificationData.onSuccess {
                    NotificationHandler.handleNotification(it)
                    if (NotificationType.Messages == it.type) {
                        scope.launch {
                            silentMessageRepository.sendSilentMessages(it)
                        }
                    }
                }
            }

            override fun onPushNotification(title: String?, body: String?) {
                Napier.d("Push Notification notification title: $title")
            }
        })
    }
}


expect fun onApplicationStartPlatformSpecific()