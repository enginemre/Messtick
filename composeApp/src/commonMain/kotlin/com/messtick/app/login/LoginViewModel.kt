package com.messtick.app.login

import androidx.lifecycle.viewModelScope
import com.messtick.app.core.ui.BaseViewModel
import com.messtick.app.core.ui.ViewEffect
import com.messtick.app.core.ui.ViewEvent
import com.messtick.app.core.ui.ViewState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

class LoginViewModel() : BaseViewModel<LoginState, LoginEvent, LoginEffect>() {

    override fun createInitialState(): LoginState {
        return LoginState()
    }

    override fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.OnLoginClick -> viewModelScope.launch {
                sendEffect(LoginEffect.NavigateLoginOAuth)
            }
        }
    }



}

data class LoginState(
    val loading: Boolean = false,
) : ViewState

sealed interface LoginEvent : ViewEvent {
    data object OnLoginClick: LoginEvent
}

sealed interface LoginEffect : ViewEffect {
    data object NavigateLoginOAuth : LoginEffect
}