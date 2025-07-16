package com.messtick.app.core.di

import com.messtick.app.chatdetail.di.chatDetailModule
import com.messtick.app.core.data.networking.DefaultDispatcherProvider
import com.messtick.app.core.data.networking.DispatcherProvider
import com.messtick.app.core.data.repository.SilentMessageRepositoryImpl
import com.messtick.app.core.data.usecase.GetSessionOutUseCaseImpl
import com.messtick.app.core.data.usecase.RegisterDeviceUseCaseImpl
import com.messtick.app.core.data.usecase.SyncMessagesUseCaseImpl
import com.messtick.app.core.domain.GetSessionOutUseCase
import com.messtick.app.core.domain.RegisterDeviceUseCase
import com.messtick.app.core.domain.SyncMessagesUseCase
import com.messtick.app.core.domain.repository.SilentMessageRepository
import com.messtick.app.home.di.homeModule
import com.messtick.app.login.di.loginModule
import com.messtick.app.splash.di.splashModule
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val messtickAppModule = module {
    singleOf(::DefaultDispatcherProvider) { bind<DispatcherProvider>() }
    singleOf(::GetSessionOutUseCaseImpl).bind<GetSessionOutUseCase>()
    singleOf(::RegisterDeviceUseCaseImpl).bind<RegisterDeviceUseCase>()
    singleOf(::SilentMessageRepositoryImpl).bind<SilentMessageRepository>()
    singleOf(::SyncMessagesUseCaseImpl).bind<SyncMessagesUseCase>()
}

val messtickModules = listOf(
    messtickAppModule,
    splashModule,
    loginModule,
    networkModule,
    platformModule,
    persistenceModule,
    homeModule,
    chatDetailModule
)
