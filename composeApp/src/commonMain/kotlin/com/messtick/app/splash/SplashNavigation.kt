package com.messtick.app.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val splashRoute = "splash"

fun NavGraphBuilder.splash(
    navigateHome: () -> Unit,
    navigateLogin: () -> Unit
) {
    composable(
        splashRoute
    ) {
        SplashRoute(
            navigateLogin = navigateLogin,
            navigateHome = navigateHome
        )
    }
}