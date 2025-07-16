package com.messtick.app.chatdetail.domain

import kotlinx.datetime.Instant

data class ChatDetailModel(
    val guid : String,
    val message: String,
    val date : Instant?,
    val messageOwnerId : String?,
    val messageOwnerName : String?,
    val chatOwnerId : String?,
)
