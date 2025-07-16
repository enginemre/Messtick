package com.messtick.app.core.data.usecase

import com.messtick.app.core.data.networking.DispatcherProvider
import com.messtick.app.core.data.persistent.UserDataStore
import com.messtick.app.core.domain.GetSessionOutUseCase
import kotlinx.coroutines.flow.Flow

class GetSessionOutUseCaseImpl(
    private val userDataStore: UserDataStore,
    private val dispatcherProvider: DispatcherProvider
) : GetSessionOutUseCase {
    override fun invoke(): Flow<Unit> = userDataStore.sessionOutFlow
}