package com.messtick.app

import androidx.compose.runtime.Composable
import com.messtick.app.core.di.messtickModules
import com.messtick.app.core.navigation.MesstickNavHost
import com.messtick.app.core.ui.MesstickTheme
import org.koin.compose.KoinApplication


@Composable
fun App() {
    KoinApplication(
        application = { modules(messtickModules) }
    ) {
        MesstickTheme {
            MesstickNavHost()
        }
    }
}