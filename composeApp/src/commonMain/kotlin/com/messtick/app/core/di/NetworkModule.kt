package com.messtick.app.core.di

import com.messtick.app.core.data.networking.HttpClientFactory
import com.messtick.app.core.data.networking.api.CoreRemoteDataSource
import com.messtick.app.core.data.networking.api.CoreRemoteDataSourceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    singleOf(::HttpClientFactory)
    single { get<HttpClientFactory>().create() }
    singleOf(::CoreRemoteDataSourceImpl) { bind<CoreRemoteDataSource>() }
}