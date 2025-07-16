package com.messtick.app.previewparameterprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.messtick.app.chatdetail.ui.ChatDetailState
import com.messtick.app.chatdetail.ui.model.ChatDetailUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ChatDetailPreviewParameterProvider : PreviewParameterProvider<ChatDetailScenario> {
    override val values: Sequence<ChatDetailScenario>
        get() = sequenceOf(
            ChatDetailScenario(
                state = ChatDetailState(),
                messages = flowOf(
                    PagingData.from(
                        listOf(
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "ss",
                            ),
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "",
                            ),

                        ),
                        sourceLoadStates =
                        LoadStates(
                            refresh = LoadState.NotLoading(false),
                            append = LoadState.NotLoading(false),
                            prepend = LoadState.NotLoading(false),
                        ),
                    ),
                )
            ),
            ChatDetailScenario(
                state = ChatDetailState(),
                messages = flowOf(
                    PagingData.from(
                        listOf(
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "ss",
                            ),
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "",
                            )
                        ),
                        sourceLoadStates =
                        LoadStates(
                            refresh = LoadState.Error(Exception()),
                            append = LoadState.NotLoading(false),
                            prepend = LoadState.NotLoading(false),
                        ),
                    ),
                )
            ),
            ChatDetailScenario(
                state = ChatDetailState(),
                messages = flowOf(
                    PagingData.from(
                        listOf(
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "ss",
                            ),
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "",
                            ),
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "ss",
                            ),
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "",
                            ),
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "ss",
                            ),
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "",
                            ),
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "ss",
                            ),
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "",
                            ),
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "ss",
                            ),
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "",
                            ),
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "ss",
                            ),
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "",
                            ),
                        ),
                        sourceLoadStates =
                        LoadStates(
                            refresh = LoadState.NotLoading(false),
                            prepend = LoadState.Loading,
                            append = LoadState.NotLoading(false),

                        ),
                    ),
                )
            ),
            ChatDetailScenario(
                state = ChatDetailState(),
                messages = flowOf(
                    PagingData.from(
                        listOf(
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "ss",
                            ),
                            ChatDetailUiModel(
                                guid = "",
                                message = "Selam",
                                date = "",
                                messageOwnerId = "ss",
                                messageOwnerName = null,
                                chatOwnerId = "",
                            )
                        ),
                        sourceLoadStates =
                        LoadStates(
                            refresh = LoadState.NotLoading(false),
                            append = LoadState.NotLoading(false),
                            prepend = LoadState.Error(Exception("Sess")),
                        ),
                    ),
                )
            ),
        )
}

data class ChatDetailScenario(
    val state: ChatDetailState,
    val messages: Flow<PagingData<ChatDetailUiModel>>
)