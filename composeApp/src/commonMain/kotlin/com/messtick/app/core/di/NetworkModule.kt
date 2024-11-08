package com.messtick.app.core.di

import com.messtick.app.core.data.networking.HttpClientFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    singleOf(::HttpClientFactory)
    single { get<HttpClientFactory>().create() }
}