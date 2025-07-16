package com.messtick.app.core.domain

import kotlinx.coroutines.flow.Flow

interface RegisterDeviceUseCase {
    operator fun invoke(token: String): Flow<Unit>
}