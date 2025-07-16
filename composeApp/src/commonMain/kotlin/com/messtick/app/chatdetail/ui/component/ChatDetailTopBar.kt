package com.messtick.app.chatdetail.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.messtick.app.chatdetail.ui.ChatDetailEvent
import com.messtick.app.core.ui.theme.primaryLight
import messtick.composeapp.generated.resources.Res
import messtick.composeapp.generated.resources.delete_chat
import messtick.composeapp.generated.resources.mute_chat
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailTopBar(
    chatName: String,
    onViewEvent: (ChatDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        title = {
            Text(
                text = chatName,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onViewEvent(ChatDetailEvent.OnBackClick) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            var showDropDown by remember { mutableStateOf(false) }
            IconButton(
                onClick = {
                    showDropDown = !showDropDown
                }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null
                )
            }
            DropdownMenu(
                expanded = showDropDown,
                onDismissRequest = { showDropDown = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(Res.string.delete_chat),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = {
                        showDropDown = false
                        onViewEvent(ChatDetailEvent.OnDeleteChatClick)
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(Res.string.mute_chat),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = {
                        showDropDown = false
                        onViewEvent(ChatDetailEvent.OnMuteChatClick)
                    }
                )
            }
        }
    )
}