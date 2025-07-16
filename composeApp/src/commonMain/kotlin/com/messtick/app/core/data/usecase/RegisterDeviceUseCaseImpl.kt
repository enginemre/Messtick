package com.messtick.app.core.data.usecase

import com.messtick.app.core.data.device.DeviceInfoManager
import com.messtick.app.core.data.networking.api.CoreRemoteDataSource
import com.messtick.app.core.domain.RegisterDeviceUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterDeviceUseCaseImpl(
    private val coreRemoteDataSource: CoreRemoteDataSource,
    private val deviceInfoManager: DeviceInfoManager
) : RegisterDeviceUseCase {
    override fun invoke(token: String): Flow<Unit> = flow {
        coreRemoteDataSource.registerDeviceId(token, deviceId = deviceInfoManager.getDeviceId())
        emit(Unit)
    }
}