package com.messtick.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.messtick.app.core.data.persistent.UserDataStore
import com.messtick.app.core.domain.SyncMessagesUseCase
import com.messtick.app.core.navigation.MesstickNavHost
import com.messtick.app.core.ui.MesstickTheme
import com.messtick.app.notification.NotificationInitializer
import org.koin.compose.getKoin


@Composable
fun App() {
    val userDataStore = getKoin().get<UserDataStore>()
    val syncMessages = getKoin().get<SyncMessagesUseCase>()
    LaunchedEffect(Unit) {
        syncMessages.invoke()
    }
    NotificationInitializer()

    MesstickTheme {
        MesstickNavHost(
            sessionOutFlow = userDataStore.sessionOutFlow,
        )
    }
}