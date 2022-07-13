package com.trakclok.android.utils.extension

import androidx.compose.ui.graphics.Color

/**
 * convert hex string to compose color
 */
fun String.toComposeColor(): Color{
    return Color(android.graphics.Color.parseColor(this))
}