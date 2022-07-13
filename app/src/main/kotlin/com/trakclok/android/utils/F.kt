package com.trakclok.android.utils

import com.trakclok.android.mapping.objects.ObjectTime
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit


object F {

    /**
     * return current date only
     */
    fun currentDate(): String {
        val c: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("dd", Locale.getDefault())
        return df.format(c)
    }

    /**
     * return current day only
     */
    fun currentDay(): String {
        val c: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("EEEE", Locale.getDefault())
        return df.format(c)
    }

    /**
     * return current month only
     */
    fun currentMonth(): String {
        val c: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("MMMM", Locale.getDefault())
        return df.format(c)
    }

    /**
     * keeping on returning latest time
     * calculated by current time - given time
     * @param time - in milliseconds since epoch
     */
    fun parseLongTime(time: Long): ObjectTime {

        val current = System.currentTimeMillis()
        val diff = current - time

        val seconds = TimeUnit.MILLISECONDS.toSeconds(diff)
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val months = days / 30
        val years = days / 365

        return ObjectTime(
            seconds = "0${seconds.mod(60)}".takeLast(2),
            minutes = "0${minutes.mod(60)}".takeLast(2),
            hours = "0${hours.mod(24)}".takeLast(2),
            days = days.mod(30).toString(),
            months = months.mod(12).toString(),
            years = years.toString(),
        )
    }
}
