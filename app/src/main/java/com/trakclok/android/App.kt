package com.trakclok.android

import android.app.Application

class App : Application() {
    companion object {
        lateinit var context: Application
    }

    init {
        context = this
    }

    /**
     * on create of application
     */
    override fun onCreate() {
        super.onCreate()
    }
}