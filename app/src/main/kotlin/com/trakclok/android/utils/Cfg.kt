package com.trakclok.android.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController

@SuppressLint("StaticFieldLeak")
object Cfg {
    lateinit var navigation: NavHostController // nav controller

    var topInset = 0 // top display cutout inset
    val darkMode: MutableState<DarkMode> = mutableStateOf(DarkMode.System) // current dark mode status
}