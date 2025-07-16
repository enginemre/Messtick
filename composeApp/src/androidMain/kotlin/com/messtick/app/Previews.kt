package com.messtick.app

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import app.cash.paging.compose.collectAsLazyPagingItems
import com.messtick.app.chatdetail.ui.ChatDetailScreen
import com.messtick.app.core.ui.MesstickTheme
import com.messtick.app.home.ui.HomeScreen
import com.messtick.app.home.ui.HomeState
import com.messtick.app.previewparameterprovider.ChatDetailPreviewParameterProvider
import com.messtick.app.previewparameterprovider.ChatDetailScenario

@Preview
@Composable
private fun ChatDetailScreenPreview(
    @PreviewParameter(ChatDetailPreviewParameterProvider::class) scenario: ChatDetailScenario
) {
    MesstickTheme {
        ChatDetailScreen(
            state = scenario.state,
            messages = scenario.messages.collectAsLazyPagingItems(),
            chatName = "Chat Name",
            onViewEvent = {},
            snackbarHostState = SnackbarHostState()
        )
      /*  HomeScreen(
            state = HomeState(),
            chats = listOf(),
            onViewEvent = {}
        )*/
    }
}

