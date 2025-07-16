package com.messtick.app.chatdetail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemContentType
import com.messtick.app.chatdetail.ui.component.ChatDetailTopBar
import com.messtick.app.chatdetail.ui.component.ChatTextField
import com.messtick.app.chatdetail.ui.component.MessageBox
import com.messtick.app.chatdetail.ui.model.ChatDetailUiModel
import com.messtick.app.core.ui.component.ErrorContent
import com.messtick.app.core.ui.component.Loading
import com.messtick.app.core.ui.util.ObserveAsEffect
import com.mohamedrejeb.richeditor.model.RichTextState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun ChatDetailRoute(
    chatId: String,
    chatName: String?,
    navigateBack: () -> Unit,
) {
    val viewModel =
        koinViewModel<ChatDetailViewModel>(parameters = { parametersOf(chatId, chatName) })
    val state by viewModel.state.collectAsStateWithLifecycle()
    val messages = viewModel.chatsWithTemporaryMessages.collectAsLazyPagingItems()

    val snackbarHost = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    ObserveAsEffect(
        effects = viewModel.uiEffect
    ) { effect ->
        when (effect) {
            ChatDetailEffect.NavigateBack -> navigateBack()
            ChatDetailEffect.RefreshChat -> messages.refresh()
            is ChatDetailEffect.ShowSnackBar -> {
                scope.launch {
                    val message = getString(effect.id)
                    snackbarHost.showSnackbar(message = message)
                }
            }
        }
    }

    ChatDetailScreen(
        state = state,
        snackbarHostState = snackbarHost,
        messages = messages,
        chatName = chatName ?: "Chat Detail",
        onViewEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalUuidApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    state: ChatDetailState,
    snackbarHostState: SnackbarHostState,
    chatName: String,
    messages: LazyPagingItems<ChatDetailUiModel>,
    onViewEvent: (ChatDetailEvent) -> Unit,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            ChatDetailTopBar(
                chatName = chatName,
                onViewEvent = onViewEvent
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets.ime
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                reverseLayout = true
            ) {
                when (val refreshState = messages.loadState.refresh) {
                    is LoadState.Error -> item {
                        ErrorContent(
                            modifier = Modifier.fillParentMaxSize().padding(16.dp),
                            message = refreshState.error.message ?: "Unknown error"
                        )
                    }

                    LoadState.Loading -> Unit

                    is LoadState.NotLoading -> {
                        items(
                            count = messages.itemCount,
                            key = { index -> index },
                            contentType = messages.itemContentType()
                        ) { index ->
                            messages[index]?.let { message ->
                                Row(
                                    modifier = Modifier.fillMaxWidth().animateItem(),
                                    horizontalArrangement = if (message.fromChatOwner) Arrangement.End else Arrangement.Start
                                ) {
                                    MessageBox(
                                        modifier = Modifier.padding(8.dp).fillMaxWidth(0.75f)
                                            .wrapContentWidth(
                                                if (message.fromChatOwner) Alignment.End else Alignment.Start
                                            ),
                                        message = RichTextState().setMarkdown(message.message),
                                        fromChatOwner = message.fromChatOwner
                                    )
                                }
                            }
                        }
                    }
                }
                when (val prependState = messages.loadState.prepend) {
                    LoadState.Loading -> item {
                        Loading(
                            modifier = Modifier.fillMaxWidth().padding(24.dp)
                        )
                    }

                    is LoadState.Error -> item {
                        ErrorContent(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            message = prependState.error.message ?: "Unknown Error",
                        )
                    }

                    is LoadState.NotLoading -> Unit
                }
            }
            HorizontalDivider()
            ChatTextField(
                modifier = Modifier.padding(bottom = 8.dp),
                onSendClick = { onViewEvent(ChatDetailEvent.OnSendClick(it)) }
            )
        }

    }
}