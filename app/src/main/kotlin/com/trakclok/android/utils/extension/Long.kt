package com.trakclok.android.utils.extension

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * convert milliseconds to local date
 */
fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}