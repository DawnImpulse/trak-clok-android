package com.trakclok.android.utils

import java.text.SimpleDateFormat
import java.util.*


object F {

    /**
     * return current date only
     */
    fun currentDate(): String{
        val c: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("dd", Locale.getDefault())
        return df.format(c)
    }

    /**
     * return current day only
     */
    fun currentDay(): String{
        val c: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("EEEE", Locale.getDefault())
        return df.format(c)
    }

    /**
     * return current month only
     */
    fun currentMonth(): String{
        val c: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("MMMM", Locale.getDefault())
        return df.format(c)
    }
}