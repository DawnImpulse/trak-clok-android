package com.trakclok.android.ui.item

import androidx.annotation.RawRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.trakclok.android.utils.Cfg
import com.trakclok.android.utils.DarkMode

@Composable
fun ItemRaw(@RawRes light: Int, @RawRes dark: Int): Int {
    return if (Cfg.darkMode.value == DarkMode.Dark || isSystemInDarkTheme()) dark
    else light
}