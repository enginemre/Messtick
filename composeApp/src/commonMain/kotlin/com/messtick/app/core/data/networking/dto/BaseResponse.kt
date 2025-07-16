package com.messtick.app.core.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface BaseResponse {
    abstract val isSuccess: Boolean
    abstract val friendlyMessage: FriendlyMessage?
    abstract val serverTime: Long
}

@Serializable
data class FriendlyMessage(
    @SerialName("title")
    val title: String,
    @SerialName("message")
    val message : String
)
