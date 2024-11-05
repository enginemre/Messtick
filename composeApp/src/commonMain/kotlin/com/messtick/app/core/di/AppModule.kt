package com.messtick.app.core.di

import com.messtick.app.core.data.networking.DefaultDispatcherProvider
import com.messtick.app.core.data.networking.DispatcherProvider
import com.messtick.app.login.di.loginModule
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val messtickAppModule = module {
    singleOf(::DefaultDispatcherProvider) { bind<DispatcherProvider>() }
}

val messtickModules = listOf(
    messtickAppModule,
    loginModule,
    networkModule,
)
