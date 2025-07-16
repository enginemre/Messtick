package com.messtick.app.home.data.dto

import com.messtick.app.core.data.networking.dto.BaseResponse
import com.messtick.app.core.data.networking.dto.FriendlyMessage
import kotlinx.serialization.Serializable

@Serializable
data class GetChatResponse(
    val data: List<ChatItemDto>?,
    override val isSuccess: Boolean,
    override val friendlyMessage: FriendlyMessage?,
    override val serverTime: Long
) : BaseResponse

@Serializable
data class ChatItemDto(
    val guid: String,
    val name: String?,
    val message: String?,
    val clientName: String?,
    val clientGuid: String?,
    val unread: Boolean
)