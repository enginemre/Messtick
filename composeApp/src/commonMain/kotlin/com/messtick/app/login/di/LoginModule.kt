package com.messtick.app.login.di

import com.messtick.app.login.LoginViewModel
import com.messtick.app.login.data.LoginRemoteDataSource
import com.messtick.app.login.data.LoginRemoteDataSourceImpl
import com.messtick.app.login.data.LoginUseCaseImpl
import com.messtick.app.login.domain.LoginUseCase
import com.messtick.app.login.loginoauth.LoginOAuthViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::LoginOAuthViewModel)
    singleOf(::LoginUseCaseImpl) { bind<LoginUseCase>() }
    singleOf(::LoginRemoteDataSourceImpl) { bind<LoginRemoteDataSource>() }
}
