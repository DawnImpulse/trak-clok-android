package com.trakclok.android.utils.extension

import android.util.Log
import com.trakclok.android.BuildConfig
import com.trakclok.android.utils.NAME
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

/**
 * convert local date to milliseconds since epoch
 */
fun LocalDate.toMilli(): Long {
    val instant = this.atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant()
    return instant.toEpochMilli()
}

/**
 * convert local date time to milliseconds since epoch
 */
fun LocalDateTime.toMilli(): Long {
    val instant = this.atZone(ZoneId.systemDefault()).toInstant()
    return instant.toEpochMilli()
}

// logging
object log {

    // debug
    fun d(message: String) {
        if (BuildConfig.DEBUG) Log.d(NAME, message)
    }

    // error
    fun e(message: String) {
        if (BuildConfig.DEBUG) Log.e(NAME, message)
    }
}