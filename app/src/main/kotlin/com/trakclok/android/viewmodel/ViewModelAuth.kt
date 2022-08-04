package com.trakclok.android.viewmodel

import android.app.Activity
import android.content.Context
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.trakclok.android.helpers.HelperAuth
import com.trakclok.android.helpers.HelperGoogle
import com.trakclok.android.utils.Cfg
import com.trakclok.android.utils.Route

class ViewModelAuth : ViewModel() {
    val loading = mutableStateOf(false)

    /**
     * start google login
     * @param context
     * @param activityResultLauncher
     */
    fun startGoogleLogin(
        context: Context,
        activityResultLauncher: ActivityResultLauncher<IntentSenderRequest>
    ) {
        loading.value = true
        HelperGoogle.init(context as Activity, activityResultLauncher) {
            it?.let { loading.value = false }
        }
    }

    /**
     * finalize google authentication
     */
    fun finishGoogleLogin(activity: Activity, result: ActivityResult) {
        // ---- finalize signin
        HelperGoogle.finalize(activity, result) {
            // ---- if auth then navigate to user
            if (it == null) {
                Cfg.navigation.popBackStack()
                Cfg.navigation.navigate(Route.Home.label)
            }
            // ---- dialog dismiss
            loading.value = false
        }
    }
}