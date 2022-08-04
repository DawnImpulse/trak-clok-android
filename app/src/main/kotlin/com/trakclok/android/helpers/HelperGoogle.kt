package com.trakclok.android.helpers

import android.app.Activity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.trakclok.android.BuildConfig
import com.trakclok.android.R
import com.trakclok.android.utils.Errors
import com.trakclok.android.utils.extension.log
import com.trakclok.android.utils.extension.toast

object HelperGoogle {
    private lateinit var oneTapClient: SignInClient

    /**
     * initialize sign in
     * @param activity
     * @param activityResultLauncher
     * @param callback - null for success
     */
    fun init(
        activity: Activity,
        activityResultLauncher: ActivityResultLauncher<IntentSenderRequest>,
        callback: (Int?) -> Unit
    ) {

        // --- setup one tap client
        oneTapClient = Identity.getSignInClient(activity)

        // --- create sign in request
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(BuildConfig.GOOGLE_WEB_TOKEN)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()

        // --- start sign in
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(activity) { result ->
                try {
                    // --- open one-tap ui
                    val intent =
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    activityResultLauncher.launch(intent)
                    callback(null)
                }
                // --- fail to show one-tap ui
                catch (e: Exception) {
                    log.e("Couldn't start One Tap UI: ${e.localizedMessage}")
                    toast.error(
                        "failed to show google sign-in interface (${Errors.google_no_ui}); please try again/contact us",
                        true
                    )
                    callback(Errors.google_ui_error)
                }
            }
            .addOnFailureListener(activity) { e ->
                toast.error("(${Errors.google_ui_error}) ${e.localizedMessage}", true)
                callback(Errors.google_ui_error)
            }
    }

    /**
     * finalize auth with google
     * @param activity
     * @param result
     * @param callback
     */
    fun finalize(activity: Activity, result: ActivityResult, callback: (Int?) -> Unit) {

        // ---- if successful
        if (result.resultCode == Activity.RESULT_OK) {

            // --- get credentials
            val credential = oneTapClient.getSignInCredentialFromIntent(result.data)

            // --- check if id token exists
            if (credential.googleIdToken != null) {
                // --- continue with firebase
                firebaseAuthWithGoogle(activity, credential.googleIdToken!!, callback)
            }
            // --- missing credential id token
            else {
                log.e("missing credentials google id token")
                toast.error(
                    "issue with google credentials (${Errors.google_missing_token}); please try again",
                    true
                )
                callback(Errors.google_missing_token)
            }
        }

        // --- sign in failed
        else {
            toast.error(
                "Google sign in failed (${Errors.google_intent}); try again/contact us",
                true
            )
            callback(Errors.google_intent)
        }
    }

    /**
     * link google with firebase
     * @param activity
     * @param idToken
     * @param callback
     */
    private fun firebaseAuthWithGoogle(
        activity: Activity,
        idToken: String,
        callback: (Int?) -> Unit
    ) {

        // ---- get credential
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        // ---- start firebase authentication
        HelperAuth.auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                // --- signed successfully
                if (task.isSuccessful) {
                    toast.success("Welcome ${HelperAuth.user().displayName}")
                    callback(null)

                    // --- set user id for crashlytics
                    Firebase.crashlytics.setUserId(HelperAuth.user().uid)
                }
                // --- if login failed
                else {
                    // --- if sign in fails, display a message to the user.
                    log.e("signInWithCredential:failure - ${task.exception}")
                    toast.error(
                        "Some issue while logging in (${Errors.google_firebase}); try again/contact us",
                        true
                    )
                    // --- callback
                    callback(Errors.google_firebase)

                    // --- logout from google
                    oneTapClient.signOut()
                }
            }
    }

}