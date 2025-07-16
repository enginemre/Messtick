package com.messtick.app.core.data.networking.api

interface CoreRemoteDataSource {
    suspend fun registerDeviceId(pushToken: String, deviceId: String)

    companion object {
        const val REGISTER_DEVICE = "/api/login/registerDevice"
    }
}