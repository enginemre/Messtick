package com.messtick.app.chatdetail.ui.model

import kotlinx.datetime.Instant

data class ChatDetailUiModel(
    val guid: String,
    val message: String,
    val date: String?,
    val messageOwnerId: String?,
    val messageOwnerName: String?,
    val chatOwnerId: String?,
) {
    val fromChatOwner = chatOwnerId != null && messageOwnerId == chatOwnerId
}