package com.trakclok.android.utils.extension

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.ui.graphics.Color
import com.trakclok.android.BuildConfig
import com.trakclok.android.helpers.HelperToast
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

/**
 * run on main thread
 */
fun onMain(fn: () -> Unit) {
    Handler(Looper.getMainLooper()).post(fn)
}

/**
 * convert px from dp
 * @param px
 */
fun Context.pxToDp(px: Float): Float {
    return px / resources.displayMetrics.density
}

/**
 * convert dp to px
 * @param dp
 */
fun Context.dpToPx(dp: Float): Float {
    return dp * resources.displayMetrics.density
}

// show sheet slowly
@ExperimentalMaterialApi
suspend fun ModalBottomSheetState.showAnim(){
    this.animateTo(
        ModalBottomSheetValue.Expanded,
        anim = tween(800)
    )
}

// hide sheet slowly
@ExperimentalMaterialApi
suspend fun ModalBottomSheetState.hideAnim(){
    this.animateTo(
        ModalBottomSheetValue.Hidden,
        anim = tween(800)
    )
}

/**
 * convert color to hex code
 */
fun Color.toHexCode(): String {
    val red = this.red * 255
    val green = this.green * 255
    val blue = this.blue * 255
    return String.format("#%02x%02x%02x", red.toInt(), green.toInt(), blue.toInt())
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

// toast
object toast {

    // info
    fun info(message: String, long: Boolean = false){
        onMain { HelperToast.info(message, long) }
    }

    // error
    fun error(message: String, long: Boolean = false){
        onMain { HelperToast.error(message, long) }
    }

    // success
    fun success(message: String, long: Boolean = false){
        onMain { HelperToast.success(message, long) }
    }
}