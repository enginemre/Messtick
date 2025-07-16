package com.messtick.app.core.domain

import kotlinx.coroutines.flow.Flow

interface GetSessionOutUseCase {
    operator fun invoke() : Flow<Unit>
}