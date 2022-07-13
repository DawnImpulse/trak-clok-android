package com.trakclok.android.database

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.internal.common.CrashlyticsCore
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.trakclok.android.mapping.objects.ObjectProject
import com.trakclok.android.utils.Cfg
import java.lang.Exception

object RealtimeProjects {

    /**
     * get list of projects for a user
     */
    suspend fun getList(): List<ObjectProject> {
        // --- get response from path
        val response = Cfg.realtimeGeneric.dataOnce("users/abcd/projects")

        // --- parse response
        return response.children.map {
            it.getValue(ObjectProject::class.java)!!
        }
    }
}