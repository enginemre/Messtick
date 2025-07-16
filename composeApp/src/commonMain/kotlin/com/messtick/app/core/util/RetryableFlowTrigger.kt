package com.messtick.app.core.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onSubscription

class RetryableFlowTrigger {
    internal val retryEvent: MutableStateFlow<RetryEvent> = MutableStateFlow(RetryEvent.INITIAL)

    fun retry() {
        retryEvent.value = RetryEvent.RETRYING
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> RetryableFlowTrigger.retryableFlow(flowProvider: RetryableFlowTrigger.() -> Flow<T>): Flow<T> =
    retryEvent
        .onSubscription {
            retryEvent.value = RetryEvent.INITIAL
        }.filter {
            it == RetryEvent.RETRYING || it == RetryEvent.INITIAL
        }.flatMapLatest {
            flowProvider.invoke(this)
        }.onEach {
            retryEvent.value = RetryEvent.IDLE
        }


internal enum class RetryEvent {
    RETRYING,
    INITIAL,
    IDLE
}