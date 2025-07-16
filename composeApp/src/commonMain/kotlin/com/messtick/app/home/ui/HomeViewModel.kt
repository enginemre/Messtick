package com.messtick.app.home.ui

import androidx.lifecycle.viewModelScope
import com.messtick.app.core.data.networking.Resource
import com.messtick.app.core.data.worker.SyncMessageWorker
import com.messtick.app.core.domain.RegisterDeviceUseCase
import com.messtick.app.core.domain.repository.SilentMessageRepository
import com.messtick.app.core.ui.BaseViewModel
import com.messtick.app.core.ui.ViewEffect
import com.messtick.app.core.ui.ViewEvent
import com.messtick.app.core.ui.ViewState
import com.messtick.app.core.util.RetryEvent
import com.messtick.app.core.util.RetryableFlowTrigger
import com.messtick.app.core.util.retryableFlow
import com.messtick.app.home.domain.model.ChatItem
import com.messtick.app.home.domain.usecase.GetChatsUseCase
import com.messtick.app.login.domain.LogoutUseCase
import com.mmk.kmpnotifier.notification.NotifierManager
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val registerDeviceUseCase: RegisterDeviceUseCase,
    private val getChatsUseCase: GetChatsUseCase,
    private val logoutUseCase: LogoutUseCase,
    silentMessageRepository: SilentMessageRepository
) : BaseViewModel<HomeState, HomeEvent, HomeEffect>() {

    private val retryableFlowTrigger = RetryableFlowTrigger()
    private var silentJob: Job? = null

    val chats = retryableFlowTrigger.retryableFlow {
        getChatsUseCase().map { resource ->
            processChatResource(
                resource = resource,
                isRetrying = retryEvent.value == RetryEvent.RETRYING
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = persistentListOf()
    )

    init {
        silentJob =
            silentMessageRepository.silentMessages.onEach { fetchData() }.launchIn(viewModelScope)

    }


    override fun createInitialState(): HomeState {
        return HomeState()
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnChatClick -> viewModelScope.launch {
                sendEffect(HomeEffect.NavigateToChat(event.id, event.name))
            }

            HomeEvent.OnLogout -> {
                logoutUseCase().onEach {
                    if (it is Resource.Success) {
                        sendEffect(HomeEffect.NavigateLogin)
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private suspend fun processChatResource(
        resource: Resource<List<ChatItem>>,
        isRetrying: Boolean
    ): PersistentList<ChatItem> {
        setState {
            copy(
                loading = resource is Resource.Loading && isRetrying.not(),
                errorMessage = ""
            )
        }
        when (resource) {
            is Resource.Error -> {
                setState { copy(errorMessage = resource.exception?.message.orEmpty()) }
            }

            Resource.Loading -> Unit
            is Resource.Success -> {
                if (isRetrying.not()) {
                    NotifierManager.getPushNotifier().getToken()?.let { token ->
                        registerDeviceUseCase(token).launchIn(viewModelScope)
                    }
                }
                return resource.data.toPersistentList()
            }
        }
        return if (isRetrying) {
            chats.value
        } else {
            persistentListOf()
        }
    }

    fun fetchData() {
        retryableFlowTrigger.retry()
    }

    override fun onCleared() {
        super.onCleared()
        silentJob?.cancel()
    }

}

data class HomeState(
    val loading: Boolean = false,
    val errorMessage: String = "",
) : ViewState

sealed interface HomeEvent : ViewEvent {
    data object OnLogout : HomeEvent

    data class OnChatClick(val id: String, val name: String) : HomeEvent
}

sealed interface HomeEffect : ViewEffect {
    data object NavigateLogin : HomeEffect

    data class NavigateToChat(val id: String, val name: String) : HomeEffect

}