package com.messtick.app.login.data

import com.messtick.app.core.data.networking.DispatcherProvider
import com.messtick.app.core.data.networking.Resource
import com.messtick.app.core.data.networking.asResource
import com.messtick.app.core.data.persistent.UserDataStore
import com.messtick.app.login.domain.LogoutUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LogoutUseCaseImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val userDataStore: UserDataStore
) : LogoutUseCase {
    override fun invoke(): Flow<Resource<Boolean>> = flow {
        userDataStore.clearAll()
        emit(true)
    }.flowOn(dispatcherProvider.io).asResource()
}