package com.messtick.app.core.di

import com.messtick.app.core.data.networking.HttpClientFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    single { HttpClientFactory.create(CIO.create()) }
}