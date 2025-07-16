package com.messtick.app.chatdetail.data.pagingsource

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import com.messtick.app.chatdetail.data.ChatDetailRemoteSource
import com.messtick.app.chatdetail.domain.ChatDetailModel
import com.messtick.app.core.util.toInstant
import io.ktor.utils.io.errors.IOException

class ChatDetailPagingSource(
    private val chatId: String,
    private val chatDetailRemoteSource: ChatDetailRemoteSource,
) : PagingSource<Int, ChatDetailModel>() {
    override fun getRefreshKey(state: PagingState<Int, ChatDetailModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChatDetailModel> {
        val page = params.key ?: 1
        return try {
            val response =
                chatDetailRemoteSource.getChatDetail(
                    chatId = chatId,
                    page = page,
                    pageSize = params.loadSize
                ).getOrThrow()
            LoadResult.Page(
                data = response.data?.messages?.map {
                    ChatDetailModel(
                        message = it.message.orEmpty(),
                        date = it.createDate?.toInstant(),
                        guid = it.guid,
                        messageOwnerName = it.user.name,
                        messageOwnerId = it.user.guid,
                        chatOwnerId = response.data.meGuid
                    )
                }.orEmpty(),
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.data?.messages.isNullOrEmpty()) null else page.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(
                e
            )
        } catch (e: Exception) {
            LoadResult.Error(
                e
            )
        }
    }
}