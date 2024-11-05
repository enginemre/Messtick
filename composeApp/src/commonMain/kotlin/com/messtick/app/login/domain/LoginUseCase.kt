package com.messtick.app.login.domain

import com.messtick.app.core.data.networking.Resource
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    operator fun invoke(tempToken : String) : Flow<Resource<Boolean>>
}