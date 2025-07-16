package com.messtick.app.chatdetail.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.messtick.app.core.ui.util.enterSlideInAnimation
import com.messtick.app.core.ui.util.exitSlideOutAnimation
import com.messtick.app.core.ui.util.popEnterSlideInAnimation
import com.messtick.app.core.ui.util.popExitSlideOutAnimation

const val chatDetailRoute = "chatDetail"
const val chatIdArg = "chatIdArg"
const val chatNameArg = "chatNameArg"

fun NavController.navigateChatDetail(
    chatId: String,
    chatName: String,
    navOptions: NavOptions? = null
) {
    val route = buildString {
        append(chatDetailRoute)
        append("/$chatId")
        append("/$chatName")
    }
    this.navigate(route, navOptions)
}

fun NavGraphBuilder.chatDetail(
    navigateBack: () -> Unit,
) {
    composable(
        route = chatDetailRoute.plus("/{${chatIdArg}}").plus("/{${chatNameArg}}"),
        arguments = listOf(
            navArgument(chatIdArg) {
                type = NavType.StringType
            },
            navArgument(chatNameArg) {
                type = NavType.StringType
            }
        )
    ) {
        ChatDetailRoute(
            chatId = it.arguments?.getString(chatIdArg) ?: "",
            chatName = it.arguments?.getString(chatNameArg) ?: "",
            navigateBack = navigateBack
        )
    }
}