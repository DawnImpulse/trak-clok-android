package com.trakclok.android.utils

import com.trakclok.android.mapping.objects.ObjectTime
import java.text.SimpleDateFormat
import java.util.*


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
    fun getActiveTime(time: Long): ObjectTime {

        val current = System.currentTimeMillis()
        val diff = current - time
        val formatter = SimpleDateFormat("d M y HH mm ss");
        val dateArray = formatter.format(Date(diff)).split(" ")
        return ObjectTime(
            seconds = dateArray[5],
            minutes = dateArray[4],
            hours = dateArray[3],
            days = dateArray[0],
            months = dateArray[1],
            years = dateArray[2],
        )
    }
}