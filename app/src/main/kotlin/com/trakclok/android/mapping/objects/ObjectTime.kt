package com.trakclok.android.mapping.objects

data class ObjectTime(
    val seconds: String,
    val minutes: String = "0",
    val hours: String = "0",
    val days: String = "0",
    val months: String = "0",
    val years: String = "0"
)
