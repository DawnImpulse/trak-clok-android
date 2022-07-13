package com.trakclok.android.utils.extension

import android.util.Log
import com.trakclok.android.BuildConfig
import com.trakclok.android.utils.NAME

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