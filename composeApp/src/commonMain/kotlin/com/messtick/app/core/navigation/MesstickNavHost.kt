package com.messtick.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.messtick.app.chatdetail.ui.chatDetail
import com.messtick.app.chatdetail.ui.navigateChatDetail
import com.messtick.app.home.ui.home
import com.messtick.app.home.ui.homeRoute
import com.messtick.app.home.ui.navigateHome
import com.messtick.app.login.login
import com.messtick.app.login.loginRoute
import com.messtick.app.login.navigateLogin
import com.messtick.app.login.navigateLoginOAuth
import com.messtick.app.splash.splash
import com.messtick.app.splash.splashRoute
import kotlinx.coroutines.flow.Flow

@Composable
fun MesstickNavHost(
    sessionOutFlow: Flow<Unit>
) {
    val navController = rememberNavController()
    LaunchedEffect(Unit) {
        sessionOutFlow.collect {
            navController.navigateLogin()
        }
    }
    NavHost(
        navController = navController,
        startDestination = splashRoute,
    ) {
        splash(
            navigateHome = navController::navigateHome,
            navigateLogin = navController::navigateLogin
        )
        login(
            navigateHome = navController::navigateHome,
            navigateBack = navController::popBackStack,
            navigateLoginOAuth = navController::navigateLoginOAuth
        )
        home(
            navigateToChat = navController::navigateChatDetail,
            navigateToLogin = navController::navigateLogin
        )
        chatDetail(
            navigateBack = navController::navigateUp
        )
    }
}