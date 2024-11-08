package com.messtick.app.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.messtick.app.core.ui.util.ObserveAsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginRoute(
    navigateLoginOAuth: () -> Unit
) {
    val viewmodel = koinViewModel<LoginViewModel>()
    val state by viewmodel.state.collectAsStateWithLifecycle()
    ObserveAsEffect(effects = viewmodel.uiEffect) { effect ->
        when (effect) {
            LoginEffect.NavigateLoginOAuth -> navigateLoginOAuth()
        }
    }
    LoginScreen(
        state = state,
        onViewEvent = viewmodel::onEvent
    )
}


@Composable
fun LoginScreen(
    state: LoginState,
    onViewEvent: (LoginEvent) -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets.ime
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Şimdi giriş yaparak uygulamaya kullanmaya başlayın",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = {
                    onViewEvent(LoginEvent.OnLoginClick )
                }
            ) {
                Text(
                    text = "Giriş Yap",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
            }

        }
    }
}