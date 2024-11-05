package com.messtick.app.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.messtick.app.login.loginoauth.LoginOAuthRoute
import kotlinx.serialization.Serializable

@Serializable
data object LoginNavigation

@Serializable
data object LoginOAuthNavigation

fun NavController.navigateLoginOAuth(navOptions: NavOptions? = null) {
    this.navigate(LoginOAuthNavigation, navOptions)
}

fun NavController.navigateLogin(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        popUpTo(0) {
            inclusive = true
        }
    }
) {
    this.navigate(LoginNavigation, navOptions)
}

fun NavGraphBuilder.login(
    navigateHome: () -> Unit,
    navigateBack: () -> Unit,
    navigateLoginOAuth: () -> Unit
) {
    composable<LoginNavigation>() {
        LoginRoute(
            navigateLoginOAuth = navigateLoginOAuth
        )
    }
    composable<LoginOAuthNavigation>() {
        LoginOAuthRoute(
            navigateHome = navigateHome,
            navigateBack = navigateBack
        )
    }
}