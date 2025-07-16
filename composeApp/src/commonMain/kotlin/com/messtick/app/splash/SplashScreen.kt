package com.messtick.app.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.messtick.app.core.ui.util.ObserveAsEffect
import messtick.composeapp.generated.resources.Res
import messtick.composeapp.generated.resources.messtick
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashRoute(
    navigateHome: () -> Unit,
    navigateLogin: () -> Unit
) {
    val viewModel = koinViewModel<SplashViewModel>()
    ObserveAsEffect(
        effects = viewModel.uiEffect
    ){effect ->
        when(effect){
            SplashEffect.NavigateHome -> navigateHome()
            SplashEffect.NavigateLogin -> navigateLogin()
        }
    }
    SplashScreen()
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        Box(
            modifier = modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(160.dp),
                painter = painterResource(Res.drawable.messtick),
                contentDescription = null
            )
        }
    }

}