package com.messtick.app.login.data

import com.messtick.app.login.data.dto.LoginResponse

interface LoginRemoteDataSource {
    suspend fun loginUser(token: String): Result<LoginResponse>
}