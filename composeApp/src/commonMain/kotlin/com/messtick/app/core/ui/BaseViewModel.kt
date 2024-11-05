package com.messtick.app.core.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<State : ViewState, Event : ViewEvent, Effect : ViewEffect> :
    ViewModel() {

    private val initialState: State by lazy { createInitialState() }

    abstract fun createInitialState(): State

    private val _state = MutableStateFlow<State>(initialState)
    val state = _state.asStateFlow()

    private val _uiEffect = Channel<Effect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    protected val currentState: State get() = state.value

    protected suspend fun sendEffect(effect: Effect) {
        _uiEffect.send(effect)
    }

    abstract fun onEvent(event: Event)

    protected fun setState(reduce: State.() -> State) {
        _state.update { currentState.reduce() }
    }
}

interface ViewState

interface ViewEvent

interface ViewEffect