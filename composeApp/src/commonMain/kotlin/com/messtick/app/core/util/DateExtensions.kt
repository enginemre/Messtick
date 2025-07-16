package com.messtick.app.core.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun String.toInstant(): Instant {
    return Instant.parse(this)
}

fun Instant.toFormattedString(pattern: String, useLocalZone: Boolean = true): String {
    val timeZone = if (useLocalZone) TimeZone.currentSystemDefault() else TimeZone.UTC
    val localDateTime = this.toLocalDateTime(timeZone)
    return localDateTime.toString()
}