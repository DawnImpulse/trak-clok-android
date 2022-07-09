package com.trakclok.android.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat

@SuppressLint("StaticFieldLeak")
object Cfg {
    val darkMode: MutableState<DarkMode> = mutableStateOf(DarkMode.System) // current dark mode status
}