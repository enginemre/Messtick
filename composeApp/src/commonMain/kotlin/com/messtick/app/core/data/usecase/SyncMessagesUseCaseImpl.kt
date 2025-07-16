package com.messtick.app.core.data.usecase

import com.messtick.app.core.data.worker.SyncMessageWorker
import com.messtick.app.core.domain.SyncMessagesUseCase

class SyncMessagesUseCaseImpl(
    private val syncMessageWorker: SyncMessageWorker
) : SyncMessagesUseCase {
    override fun invoke() {
        syncMessageWorker.syncMessages()
    }
}