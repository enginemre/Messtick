package com.messtick.app

import android.app.Application
import com.messtick.app.core.util.initLogger
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class MesstickApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initLogger()
    }
}