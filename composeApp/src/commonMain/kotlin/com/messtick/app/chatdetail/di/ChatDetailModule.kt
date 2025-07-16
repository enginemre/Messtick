package com.messtick.app.chatdetail.di

import com.messtick.app.chatdetail.data.ChatDetailRemoteSource
import com.messtick.app.chatdetail.data.ChatDetailRemoteSourceImpl
import com.messtick.app.chatdetail.data.usecase.DeleteChatUseCaseImpl
import com.messtick.app.chatdetail.data.usecase.GetChatDetailUseCaseImpl
import com.messtick.app.chatdetail.data.usecase.MuteChatUseCaseImpl
import com.messtick.app.chatdetail.data.usecase.ReadMessageUseCaseImpl
import com.messtick.app.chatdetail.data.usecase.SendMessageUseCaseImpl
import com.messtick.app.chatdetail.domain.usecase.DeleteChatUseCase
import com.messtick.app.chatdetail.domain.usecase.GetChatDetailUseCase
import com.messtick.app.chatdetail.domain.usecase.MuteChatUseCase
import com.messtick.app.chatdetail.domain.usecase.ReadMessageUseCase
import com.messtick.app.chatdetail.domain.usecase.SendMessageUseCase
import com.messtick.app.chatdetail.ui.ChatDetailViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val chatDetailModule = module {
    viewModelOf(::ChatDetailViewModel)
    singleOf(::SendMessageUseCaseImpl).bind<SendMessageUseCase>()
    singleOf(::ChatDetailRemoteSourceImpl).bind<ChatDetailRemoteSource>()
    singleOf(::GetChatDetailUseCaseImpl).bind<GetChatDetailUseCase>()
    singleOf(::ReadMessageUseCaseImpl).bind<ReadMessageUseCase>()
    singleOf(::DeleteChatUseCaseImpl).bind<DeleteChatUseCase>()
    singleOf(::MuteChatUseCaseImpl).bind<MuteChatUseCase>()
}