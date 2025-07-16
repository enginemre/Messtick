package com.messtick.app.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.messtick.app.core.ui.MesstickWithoutDarkTheme
import com.messtick.app.core.ui.util.ObserveAsEffect
import messtick.composeapp.generated.resources.Res
import messtick.composeapp.generated.resources.app_name
import messtick.composeapp.generated.resources.background
import messtick.composeapp.generated.resources.login
import messtick.composeapp.generated.resources.login_description
import messtick.composeapp.generated.resources.messtick
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
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
    MesstickWithoutDarkTheme {
        LoginScreen(
            state = state,
            onViewEvent = viewmodel::onEvent
        )
    }
}


@Composable
fun LoginScreen(
    state: LoginState,
    onViewEvent: (LoginEvent) -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Res.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 70.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(110.dp),
                        painter = painterResource(Res.drawable.messtick),
                        contentDescription = null
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(Res.string.app_name),
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                        text = stringResource(Res.string.login_description),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp).height(45.dp),
                    onClick = {
                        onViewEvent(LoginEvent.OnLoginClick)
                    }
                ) {
                    Text(
                        text = stringResource(Res.string.login),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

            }
        }

    }
}