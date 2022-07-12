package com.trakclok.android

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.orhanobut.hawk.Hawk

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

        // ---- init analytics
        analytics()

        // ---- init hawk
        Hawk.init(applicationContext).build()
    }

    /**
     * enabling analytics/crashlytics in release builds
     */
    private fun analytics() {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(!BuildConfig.DEBUG)

        // ---- set user id in-case of logged in
        /*if (AuthFirebase.loggedIn() && !BuildConfig.DEBUG) {
            try {
                Firebase.crashlytics.setUserId(AuthFirebase.user()!!.uid)
            } catch (e: Exception) {
                e.printStackTrace()
                Firebase.crashlytics.recordException(e)
            }
        }*/
    }
}