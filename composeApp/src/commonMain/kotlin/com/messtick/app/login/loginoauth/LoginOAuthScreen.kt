package com.messtick.app.login.loginoauth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.zIndex
import com.messtick.app.core.ui.util.ObserveAsEffect
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import messtick.composeapp.generated.resources.Res
import messtick.composeapp.generated.resources.background
import org.jetbrains.compose.resources.painterResource
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
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        val webViewState =
            rememberWebViewState(LoginOAuthViewModel.INITIAL_OAUTH_URL)

        if (webViewState.lastLoadedUrl != null && webViewState.lastLoadedUrl!!.startsWith("https://api.dev.messtick.com/api/login/hide?token=")) {
            onViewEvent(
                LoginOAuthEvent.OnTokenReceived(
                    webViewState.lastLoadedUrl!!.substringAfter(
                        "token="
                    )
                )
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),

            ) {
            if (webViewState.loadingState != LoadingState.Finished) {
                CircularProgressIndicator(
                    modifier = Modifier.zIndex(2f).align(Alignment.Center),
                    color = Color.White
                )
            }
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Res.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
            WebView(
                state = webViewState,
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}