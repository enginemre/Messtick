package com.messtick.app

import androidx.compose.ui.window.ComposeUIViewController
import com.messtick.app.core.di.messtickModules
import org.koin.compose.KoinApplication

fun MainViewController() = ComposeUIViewController {
    KoinApplication(
        application = { modules(messtickModules) }
    ) { App() }
}