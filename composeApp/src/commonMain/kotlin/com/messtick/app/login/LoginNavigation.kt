package com.messtick.app.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.messtick.app.login.loginoauth.LoginOAuthRoute
import kotlinx.serialization.Serializable

const val loginRoute = "login"

const val loginOAuthRoute = "loginOAuth"

fun NavController.navigateLoginOAuth(navOptions: NavOptions? = null) {
    this.navigate(loginOAuthRoute, navOptions)
}

fun NavController.navigateLogin(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        popUpTo(0) {
            inclusive = true
        }
    }
) {
    this.navigate(loginRoute, navOptions)
}

fun NavGraphBuilder.login(
    navigateHome: () -> Unit,
    navigateBack: () -> Unit,
    navigateLoginOAuth: () -> Unit
) {
    composable(
        route = loginRoute
    ) {
        LoginRoute(
            navigateLoginOAuth = navigateLoginOAuth
        )
    }
    composable(
        route = loginOAuthRoute
    ) {
        LoginOAuthRoute(
            navigateHome = navigateHome,
            navigateBack = navigateBack
        )
    }
}