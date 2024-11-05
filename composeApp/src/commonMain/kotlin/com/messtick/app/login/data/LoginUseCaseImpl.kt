package com.messtick.app.login.data

import com.messtick.app.core.data.networking.DispatcherProvider
import com.messtick.app.core.data.networking.Resource
import com.messtick.app.core.data.networking.asResource
import com.messtick.app.login.domain.LoginUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginUseCaseImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val loginRemoteDataSource: LoginRemoteDataSource,
) : LoginUseCase {
    override fun invoke(tempToken: String): Flow<Resource<Boolean>> = flow {
        val result = loginRemoteDataSource.loginUser(tempToken).getOrThrow()
        emit(result.isSuccess)
    }.flowOn(dispatcherProvider.io).asResource()
}