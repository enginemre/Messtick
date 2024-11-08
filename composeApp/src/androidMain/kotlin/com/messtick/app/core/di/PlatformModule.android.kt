package com.messtick.app.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.messtick.app.core.data.persistent.DataStoreFileName
import com.messtick.app.core.data.persistent.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<DataStore<Preferences>> { dataStore(get()) }

}

fun dataStore(context: Context): DataStore<Preferences> =
    createDataStore(
        producePath = { context.filesDir.resolve(DataStoreFileName).absolutePath }
    )

