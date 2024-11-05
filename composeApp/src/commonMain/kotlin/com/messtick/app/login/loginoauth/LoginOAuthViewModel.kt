package com.messtick.app.login.loginoauth

import androidx.lifecycle.viewModelScope
import com.messtick.app.core.data.networking.Resource
import com.messtick.app.core.ui.BaseViewModel
import com.messtick.app.core.ui.ViewEffect
import com.messtick.app.core.ui.ViewEvent
import com.messtick.app.core.ui.ViewState
import com.messtick.app.login.domain.LoginUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginOAuthViewModel(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginOAuthState, LoginOAuthEvent, LoginOAuthEffect>() {
    override fun createInitialState(): LoginOAuthState {
        return LoginOAuthState(
            initialOAuthUrl = INITIAL_OAUTH_URL
        )
    }

    override fun onEvent(event: LoginOAuthEvent) {
        when (event) {
            is LoginOAuthEvent.OnTokenReceived -> {
                loginUseCase(event.token).onEach { resource ->
                    when (resource) {
                        Resource.Loading -> Unit
                        is Resource.Error -> {
                            resource.exception.printStackTrace()
                            sendEffect(LoginOAuthEffect.NavigateBack)
                        }
                        is Resource.Success -> {
                            if (resource.data) {
                                sendEffect(LoginOAuthEffect.NavigateToHome)
                            } else {
                                sendEffect(LoginOAuthEffect.NavigateBack)
                            }
                        }
                    }

                }.launchIn(viewModelScope)
            }
        }
    }

    companion object {
        const val INITIAL_OAUTH_URL =
            "https://dev.linbik.com/auth/2025/556b3898-6cbb-4488-84d8-4ce9236acabc"
    }
}

data class LoginOAuthState(
    val initialOAuthUrl: String
) : ViewState

sealed interface LoginOAuthEvent : ViewEvent {
    data class OnTokenReceived(val token: String) : LoginOAuthEvent
}

sealed interface LoginOAuthEffect : ViewEffect {
    data object NavigateToHome : LoginOAuthEffect
    data object NavigateBack : LoginOAuthEffect
}