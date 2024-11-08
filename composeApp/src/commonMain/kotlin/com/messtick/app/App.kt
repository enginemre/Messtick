package com.messtick.app

import androidx.compose.runtime.Composable
import com.messtick.app.core.navigation.MesstickNavHost
import com.messtick.app.core.ui.MesstickTheme


@Composable
fun App() {
    MesstickTheme {
        MesstickNavHost()
    }
}