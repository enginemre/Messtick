package com.messtick.app.home.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class ChatItem(
    val guid: String,
    val name: String,
    val lastMessage: String,
    val unread: Boolean,
    val imageUrl: String? = null
)