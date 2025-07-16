package com.messtick.app.core.util

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun initLogger(){
    Napier.base(DebugAntilog())
}