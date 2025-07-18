package com.messtick.app.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.messtick.app.core.data.device.DeviceInfoManager
import com.messtick.app.core.data.persistent.DataStoreFileName
import com.messtick.app.core.data.persistent.createDataStore
import com.messtick.app.core.data.worker.SyncMessageWorker
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual val platformModule: Module = module {
    single<DataStore<Preferences>> { dataStore() }
    single { SyncMessageWorker() }
    single { DeviceInfoManager() }
}


@OptIn(ExperimentalForeignApi::class)
fun dataStore(): DataStore<Preferences> = createDataStore(
    producePath = {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/$DataStoreFileName"
    }
)

