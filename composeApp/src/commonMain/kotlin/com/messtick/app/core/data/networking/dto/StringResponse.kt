package com.messtick.app.core.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class StringResponse(
    val data: String?,
    override val isSuccess: Boolean,
    override val friendlyMessage: FriendlyMessage?,
    override val serverTime: Long
) : BaseResponse