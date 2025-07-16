package com.messtick.app.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.messtick.app.core.data.device.DeviceInfoManager
import com.messtick.app.core.data.persistent.DataStoreFileName
import com.messtick.app.core.data.persistent.createDataStore
import com.messtick.app.core.data.worker.SyncMessageWorker
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single{ SyncMessageWorker(get())}
    single<DataStore<Preferences>> { dataStore(get()) }
    single { DeviceInfoManager(get()) }
}

fun dataStore(context: Context): DataStore<Preferences> =
    createDataStore(
        producePath = { context.filesDir.resolve(DataStoreFileName).absolutePath }
    )

