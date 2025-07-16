package com.messtick.app.home.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.messtick.app.core.domain.SyncMessagesUseCase
import com.messtick.app.core.ui.MesstickTheme
import com.messtick.app.core.ui.component.ErrorContent
import com.messtick.app.core.ui.component.Loading
import com.messtick.app.core.ui.lerpDp
import com.messtick.app.core.ui.util.ObserveAsEffect
import com.messtick.app.home.domain.model.ChatItem
import com.messtick.app.home.ui.component.ChatItemComponent
import com.messtick.app.home.ui.component.HomeTopAppBar
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoute(
    navigateChat: (String, String) -> Unit,
    navigateLogin: () -> Unit
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val chats by viewModel.chats.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    ObserveAsEffect(effects = viewModel.uiEffect) { effect ->
        when (effect) {
            is HomeEffect.NavigateToChat -> navigateChat(effect.id, effect.name)
            HomeEffect.NavigateLogin -> navigateLogin()
        }
    }

    HomeScreen(
        state = state,
        chats = chats,
        onViewEvent = viewModel::onEvent
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    chats: PersistentList<ChatItem>,
    onViewEvent: (HomeEvent) -> Unit,
) {
    val appbarState = rememberTopAppBarState()
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appbarState)

    val cornerRadius by animateDpAsState(
        targetValue = lerpDp(
            start = 32.dp,
            end = 0.dp,
            fraction = appbarState.collapsedFraction
        ),
    )
    Scaffold(
        topBar = {
            HomeTopAppBar(
                topAppBarScrollBehavior = scrollBehavior,
                onLogout = { onViewEvent(HomeEvent.OnLogout) }
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.primary
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .clip(shape = RoundedCornerShape(topEnd = cornerRadius, topStart = cornerRadius))
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(topEnd = cornerRadius, topStart = cornerRadius)
                ),
        ) {
            when {
                state.loading -> item { Loading(modifier = Modifier.fillParentMaxSize()) }

                state.errorMessage.isNotBlank() || chats.isEmpty() -> item {
                    ErrorContent(
                        message = state.errorMessage.ifBlank { "No chats found" },
                        modifier = Modifier.fillParentMaxSize()
                    )
                }

                else -> {
                    chats(
                        chats = chats,
                        onItemClick = {onViewEvent(HomeEvent.OnChatClick(it.guid, it.name))}
                    )
                }
            }
        }
    }
}

private fun LazyListScope.chats(
    chats: PersistentList<ChatItem>,
    onItemClick: (ChatItem) -> Unit,
) {
    itemsIndexed(
        items = chats,
        key = { index, item -> "chat_${item.guid}" },
    ) { index, item ->
        if (index == 0) {
            Spacer(Modifier.height(12.dp))
        }
        ChatItemComponent(
            modifier = Modifier.animateItem(),
            fromPerson = item.name,
            lastMessage = item.lastMessage,
            onClick = { onItemClick(item) },
            unread = item.unread,
            imageUrl = item.imageUrl
        )
        if (index != chats.lastIndex) {
            HorizontalDivider(Modifier.padding(horizontal = 16.dp))
        }
    }
}



@Preview
@Composable
fun HomeScreenPreview() {
    MesstickTheme {
        HomeScreen(
            state = HomeState(),
            chats = persistentListOf(
                ChatItem(guid = "", name = "Emre Engin", "Merhaba Selam", unread = true)
            ),
            onViewEvent = {}
        )
    }
}

