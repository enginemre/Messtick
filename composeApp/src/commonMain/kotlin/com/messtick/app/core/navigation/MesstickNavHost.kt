package com.messtick.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.messtick.app.home.home
import com.messtick.app.home.navigateHome
import com.messtick.app.login.LoginNavigation
import com.messtick.app.login.login
import com.messtick.app.login.navigateLoginOAuth

@Composable
fun MesstickNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LoginNavigation,
    ) {
        login(
            navigateHome = navController::navigateHome,
            navigateBack = navController::popBackStack,
            navigateLoginOAuth = navController::navigateLoginOAuth
        )
        home()
    }
}