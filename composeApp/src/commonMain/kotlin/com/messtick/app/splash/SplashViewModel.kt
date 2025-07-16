package com.messtick.app.splash

import androidx.lifecycle.viewModelScope
import com.messtick.app.core.data.persistent.UserDataStore
import com.messtick.app.core.ui.BaseViewModel
import com.messtick.app.core.ui.ViewEffect
import com.messtick.app.core.ui.ViewEvent
import com.messtick.app.core.ui.ViewState
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userDataStore: UserDataStore
) : BaseViewModel<SplashState, SplashEvent, SplashEffect>() {

    init {
        viewModelScope.launch {
            val userIsLogged = userDataStore.userIsLogged()
            if (userIsLogged) {
                sendEffect(SplashEffect.NavigateHome)
            } else {
                sendEffect(SplashEffect.NavigateLogin)
            }
        }
    }

    override fun createInitialState(): SplashState {
        return SplashState()
    }

    override fun onEvent(event: SplashEvent) {
        when (event) {
            else -> {}
        }
    }
}

data class SplashState(
    val loading: Boolean = false
) : ViewState

sealed interface SplashEvent : ViewEvent

sealed interface SplashEffect : ViewEffect {
    data object NavigateLogin : SplashEffect
    data object NavigateHome : SplashEffect
}