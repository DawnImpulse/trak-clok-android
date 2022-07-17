package com.trakclok.android.helpers

import android.graphics.Color
import android.view.Gravity
import android.widget.Toast
import androidx.compose.ui.graphics.toArgb
import com.trakclok.android.App
import com.trakclok.android.ui.theme.LanquidLavender
import com.trakclok.android.ui.theme.MediumSeaGreen
import com.trakclok.android.ui.theme.PermanentGeraniumLake
import io.github.muddz.styleabletoast.StyleableToast

object HelperToast {

    // success toast
    fun success(message: String, long: Boolean = false) {
        StyleableToast.Builder(App.context)
            .text(message)
            .textColor(Color.WHITE)
            .backgroundColor(MediumSeaGreen.toArgb())
            .solidBackground()
            .gravity(Gravity.TOP)
            .length(if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
            .show()
    }

    // error toast
    fun error(message: String, long: Boolean = false) {
        StyleableToast.Builder(App.context)
            .text(message)
            .textColor(Color.WHITE)
            .backgroundColor(PermanentGeraniumLake.toArgb())
            .solidBackground()
            .gravity(Gravity.TOP)
            .length(if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
            .show()
    }

    // info toast
    fun info(message: String, long: Boolean = false) {
        StyleableToast.Builder(App.context)
            .text(message)
            .textColor(Color.BLACK)
            .backgroundColor(LanquidLavender.toArgb())
            .solidBackground()
            .gravity(Gravity.TOP)
            .length(if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
            .show()
    }
}