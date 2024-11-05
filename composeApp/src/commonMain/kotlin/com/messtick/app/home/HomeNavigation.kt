package com.messtick.app.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object HomeNavigation

fun NavController.navigateHome(navOptions: NavOptions? = null) {
    this.navigate(HomeNavigation, navOptions)
}


fun NavGraphBuilder.home() {
    composable<HomeNavigation>() {
        HomeRoute()
    }
}