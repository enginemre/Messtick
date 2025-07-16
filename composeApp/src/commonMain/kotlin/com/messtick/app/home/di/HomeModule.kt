package com.messtick.app.home.di

import com.messtick.app.home.data.GetChatUseCaseImpl
import com.messtick.app.home.data.HomeRemoteDataSource
import com.messtick.app.home.data.HomeRemoteDataSourceImpl
import com.messtick.app.home.domain.usecase.GetChatsUseCase
import com.messtick.app.home.ui.HomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeViewModel)
    singleOf(::HomeRemoteDataSourceImpl).bind<HomeRemoteDataSource>()
    singleOf(::GetChatUseCaseImpl).bind<GetChatsUseCase>()
}