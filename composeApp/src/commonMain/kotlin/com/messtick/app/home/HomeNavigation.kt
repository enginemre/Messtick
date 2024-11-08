package com.messtick.app.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val homeRoute = "home"

fun NavController.navigateHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}


fun NavGraphBuilder.home() {
    composable(
        route = homeRoute
    ) {
        HomeRoute()
    }
}