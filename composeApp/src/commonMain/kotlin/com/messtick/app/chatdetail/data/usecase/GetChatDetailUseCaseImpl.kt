package com.messtick.app.chatdetail.data.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.messtick.app.chatdetail.data.ChatDetailRemoteSource
import com.messtick.app.chatdetail.data.pagingsource.ChatDetailPagingSource
import com.messtick.app.chatdetail.domain.ChatDetailModel
import com.messtick.app.chatdetail.domain.usecase.GetChatDetailUseCase
import kotlinx.coroutines.flow.Flow

class GetChatDetailUseCaseImpl(
    private val chatDetailRemoteSource: ChatDetailRemoteSource,
) : GetChatDetailUseCase {
    override operator fun invoke(chatId: String): Flow<PagingData<ChatDetailModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                prefetchDistance = PRE_FETCH_DISTANCE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                ChatDetailPagingSource(
                    chatId = chatId,
                    chatDetailRemoteSource = chatDetailRemoteSource
                )
            },
        ).flow
    }

    companion object {
        const val PAGE_SIZE = 50
        const val PRE_FETCH_DISTANCE = 10
    }
}