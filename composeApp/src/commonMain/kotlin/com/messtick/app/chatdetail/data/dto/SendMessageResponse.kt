package com.messtick.app.chatdetail.data.dto

import com.messtick.app.core.data.networking.dto.BaseResponse
import com.messtick.app.core.data.networking.dto.FriendlyMessage
import kotlinx.serialization.Serializable

@Serializable
data class SendMessageResponse(
    val data: MessageDto?,
    override val isSuccess: Boolean,
    override val friendlyMessage: FriendlyMessage?,
    override val serverTime: Long
) : BaseResponse
