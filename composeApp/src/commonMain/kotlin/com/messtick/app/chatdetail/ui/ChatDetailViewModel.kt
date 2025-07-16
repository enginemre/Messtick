package com.messtick.app.chatdetail.ui

import androidx.lifecycle.viewModelScope
import app.cash.paging.cachedIn
import app.cash.paging.insertHeaderItem
import app.cash.paging.map
import com.messtick.app.chatdetail.data.dto.MessageDto
import com.messtick.app.chatdetail.domain.ChatDetailModel
import com.messtick.app.chatdetail.domain.ChatNotificationStatus
import com.messtick.app.chatdetail.domain.usecase.DeleteChatUseCase
import com.messtick.app.chatdetail.domain.usecase.GetChatDetailUseCase
import com.messtick.app.chatdetail.domain.usecase.MuteChatUseCase
import com.messtick.app.chatdetail.domain.usecase.ReadMessageUseCase
import com.messtick.app.chatdetail.domain.usecase.SendMessageUseCase
import com.messtick.app.chatdetail.ui.model.ChatDetailUiModel
import com.messtick.app.core.data.networking.Resource
import com.messtick.app.core.domain.repository.SilentMessageRepository
import com.messtick.app.core.ui.BaseViewModel
import com.messtick.app.core.ui.ViewEffect
import com.messtick.app.core.ui.ViewEvent
import com.messtick.app.core.ui.ViewState
import com.messtick.app.core.util.toFormattedString
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import messtick.composeapp.generated.resources.Res
import messtick.composeapp.generated.resources.delete_cannot_be_done
import messtick.composeapp.generated.resources.message_send_error
import messtick.composeapp.generated.resources.mute_error_message
import messtick.composeapp.generated.resources.mute_successful
import org.jetbrains.compose.resources.StringResource

class ChatDetailViewModel(
    private val chatId: String,
    getChatMessagesUseCase: GetChatDetailUseCase,
    silentMessageRepository: SilentMessageRepository,
    private val sendMessageUseCase: SendMessageUseCase,
    private val readMessageUseCase: ReadMessageUseCase,
    private val deleteChatUseCase: DeleteChatUseCase,
    private val muteChatUseCase: MuteChatUseCase,
) : BaseViewModel<ChatDetailState, ChatDetailEvent, ChatDetailEffect>() {

    private val _temporaryMessages = MutableStateFlow<List<ChatDetailUiModel>>(emptyList())
    private var chatOwnerId: String? = null
    private var silentJob: Job? = null
    private var readMessageJob: Job? = null

    init {
        readMessageJob = readMessageUseCase(chatId).launchIn(viewModelScope)
        silentJob = silentMessageRepository.silentMessages.onEach { data ->
            readMessageUseCase(chatId).launchIn(viewModelScope)
            data.data?.let { jsonStr ->
                val message = Json.decodeFromString<MessageDto>(jsonStr)
                if (chatId == message.chatGuid) {
                    _temporaryMessages.update {
                        it + ChatDetailUiModel(
                            message = message.message.orEmpty(),
                            date = message.createDate,
                            guid = message.guid,
                            messageOwnerId = message.user.guid,
                            messageOwnerName = message.user.name,
                            chatOwnerId = chatOwnerId
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    val chats = getChatMessagesUseCase(
        chatId = chatId,
    ).map {
        it.map { chatModel ->
            chatOwnerId = chatModel.chatOwnerId
            ChatDetailUiModel(
                message = chatModel.message,
                date = chatModel.date?.toFormattedString("yyyy-MM-dd HH:mm"),
                guid = chatModel.guid,
                messageOwnerId = chatModel.messageOwnerId,
                messageOwnerName = chatModel.messageOwnerName,
                chatOwnerId = chatModel.chatOwnerId
            )
        }
    }.cachedIn(viewModelScope)

    val chatsWithTemporaryMessages = chats.combine(_temporaryMessages) { pagingData, tempMessages ->
        var pagingDataAdded = pagingData
        tempMessages.forEach { tempMessage ->
            pagingDataAdded = pagingDataAdded.insertHeaderItem(item = tempMessage)
        }
        pagingDataAdded
    }

    override fun createInitialState(): ChatDetailState {
        return ChatDetailState()
    }

    override fun onEvent(event: ChatDetailEvent) {
        when (event) {
            ChatDetailEvent.OnBackClick -> viewModelScope.launch {
                sendEffect(ChatDetailEffect.NavigateBack)
            }

            is ChatDetailEvent.OnSendClick -> {
                sendMessage(event.message)

            }

            ChatDetailEvent.OnDeleteChatClick -> {
                deleteChat()
            }

            ChatDetailEvent.OnMuteChatClick -> {
                muteChat()
            }
        }
    }

    private fun muteChat() {
        muteChatUseCase(chatId, notificationType = ChatNotificationStatus.On).onEach { resource ->
            when(resource){
                is Resource.Error -> {
                    sendEffect(ChatDetailEffect.ShowSnackBar(Res.string.mute_error_message))
                }
                Resource.Loading -> Unit
                is Resource.Success -> {
                    sendEffect(ChatDetailEffect.ShowSnackBar(Res.string.mute_successful))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteChat() {
        deleteChatUseCase(chatId).onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    sendEffect(ChatDetailEffect.ShowSnackBar(Res.string.delete_cannot_be_done))
                }

                Resource.Loading -> Unit
                is Resource.Success -> {
                    sendEffect(ChatDetailEffect.NavigateBack)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun sendMessage(message: String) {
        if (message.isNotBlank()) {
            sendMessageUseCase(
                message = message,
                chatId = chatId,
                chatOwnerId = chatOwnerId.orEmpty()
            ).onEach {
                processMessageSendResource(it)
            }.launchIn(viewModelScope)
        }
    }

    private suspend fun processMessageSendResource(resource: Resource<ChatDetailModel?>) {
        when (resource) {
            is Resource.Error -> {}
            Resource.Loading -> {}
            is Resource.Success -> {
                if (resource.data != null) {
                    _temporaryMessages.update {
                        it + ChatDetailUiModel(
                            message = resource.data.message.orEmpty(),
                            date = resource.data.date.toString(),
                            guid = resource.data.guid.orEmpty(),
                            messageOwnerId = resource.data.messageOwnerId.orEmpty(),
                            messageOwnerName = resource.data.messageOwnerName.orEmpty(),
                            chatOwnerId = resource.data.chatOwnerId.orEmpty()
                        )
                    }
                } else {
                    sendEffect(ChatDetailEffect.ShowSnackBar(Res.string.message_send_error))
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        silentJob?.cancel()
        readMessageJob?.cancel()
    }

}

data class ChatDetailState(
    val loading: Boolean = false,
    val chatName: String = "",
) : ViewState

sealed interface ChatDetailEvent : ViewEvent {
    data object OnBackClick : ChatDetailEvent
    data object OnDeleteChatClick : ChatDetailEvent
    data object OnMuteChatClick : ChatDetailEvent

    data class OnSendClick(val message: String) : ChatDetailEvent
}

sealed interface ChatDetailEffect : ViewEffect {
    data object NavigateBack : ChatDetailEffect
    data class ShowSnackBar(val id: StringResource) : ChatDetailEffect
    data object RefreshChat : ChatDetailEffect
}