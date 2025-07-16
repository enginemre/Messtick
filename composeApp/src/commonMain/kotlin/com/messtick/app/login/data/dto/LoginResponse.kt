package com.messtick.app.login.data.dto

import com.messtick.app.core.data.networking.dto.BaseResponse
import com.messtick.app.core.data.networking.dto.FriendlyMessage
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val data: LoginDto?,
    override val isSuccess: Boolean,
    override val friendlyMessage: FriendlyMessage?,
    override val serverTime: Long
) : BaseResponse


@Serializable
data class LoginDto(
    val token: String?,
    val refreshToken: String?,
    val name: String?,
)