package com.messtick.app.login.loginoauth

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.messtick.app.core.ui.util.ObserveAsEffect
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginOAuthRoute(
    navigateHome: () -> Unit,
    navigateBack: () -> Unit
) {
    val viewModel = koinViewModel<LoginOAuthViewModel>()
    ObserveAsEffect(
        effects = viewModel.uiEffect,
    ) { effect ->
        when (effect) {
            LoginOAuthEffect.NavigateBack -> navigateBack()
            LoginOAuthEffect.NavigateToHome -> navigateHome()
        }
    }
    LoginOAuthScreen(
        onViewEvent = viewModel::onEvent
    )
}


@Composable
fun LoginOAuthScreen(
    onViewEvent: (LoginOAuthEvent) -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0,0,0,0)
    ) {
        val webViewState =
            rememberWebViewState("https://dev.linbik.com/auth/2025/556b3898-6cbb-4488-84d8-4ce9236acabc")
        if (webViewState.lastLoadedUrl != null && webViewState.lastLoadedUrl!!.startsWith("https://api.dev.messtick.com/api/login?token=")) {
            onViewEvent(
                LoginOAuthEvent.OnTokenReceived(
                    webViewState.lastLoadedUrl!!.substringAfter(
                        "token="
                    )
                )
            )
        }
        WebView(
            state = webViewState,
            modifier = Modifier.fillMaxSize()
        )
    }
}