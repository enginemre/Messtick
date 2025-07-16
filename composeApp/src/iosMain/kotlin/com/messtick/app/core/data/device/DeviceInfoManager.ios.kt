package com.messtick.app.core.data.device

import platform.UIKit.UIDevice

actual class DeviceInfoManager {
    actual fun getDeviceId(): String {
       return UIDevice.currentDevice.identifierForVendor?.UUIDString.orEmpty()
    }
}