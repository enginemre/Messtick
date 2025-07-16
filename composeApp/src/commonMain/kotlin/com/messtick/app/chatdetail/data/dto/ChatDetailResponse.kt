package com.messtick.app.chatdetail.data.dto

import com.messtick.app.core.data.networking.dto.BaseResponse
import com.messtick.app.core.data.networking.dto.FriendlyMessage
import kotlinx.serialization.Serializable

@Serializable
data class ChatDetailResponse(
    val data: ChatDetailDto?,
    override val isSuccess: Boolean,
    override val friendlyMessage: FriendlyMessage?,
    override val serverTime: Long
) : BaseResponse


@Serializable
data class ChatDetailDto(
    val messages: List<MessageDto>?,
    val meGuid : String?,
    val lastReadMessage: String?,
    val pageNumber: Int,
)

@Serializable
data class MessageDto(
    val guid: String,
    val message: String?,
    val createDate: String?,
    val subMessage: SubMessageDto?,
    val user :ChatUserDto,
    val chatGuid: String?,
)

@Serializable
data class SubMessageDto(
    val guid: String,
    val message: String?,
    val createDate: String?,
)

@Serializable
data class ChatUserDto(
    val guid: String?,
    val name: String?
)
