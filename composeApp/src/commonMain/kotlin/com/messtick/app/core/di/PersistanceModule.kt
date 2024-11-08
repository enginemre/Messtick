package com.messtick.app.core.di

import com.messtick.app.core.data.persistent.UserDataStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val persistenceModule = module {
    singleOf(::UserDataStore)
}