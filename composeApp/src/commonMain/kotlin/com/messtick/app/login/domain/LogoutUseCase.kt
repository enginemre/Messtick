package com.messtick.app.login.domain

import com.messtick.app.core.data.networking.Resource
import kotlinx.coroutines.flow.Flow

interface LogoutUseCase {
    operator fun invoke() : Flow<Resource<Boolean>>
}