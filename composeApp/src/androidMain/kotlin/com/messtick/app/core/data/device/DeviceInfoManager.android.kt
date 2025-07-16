package com.messtick.app.core.data.device

import android.content.Context
import android.provider.Settings

actual class DeviceInfoManager constructor(private val context: Context) {
    actual fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}