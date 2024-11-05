package com.messtick.app.login.data

import com.messtick.app.core.data.networking.safeCall
import com.messtick.app.login.data.dto.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url


class LoginRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : LoginRemoteDataSource {
    override suspend fun loginUser(token: String): Result<LoginResponse> = safeCall {
        httpClient.get {
            url("/api/login")
            parameter("token", token)
        }
    }
}