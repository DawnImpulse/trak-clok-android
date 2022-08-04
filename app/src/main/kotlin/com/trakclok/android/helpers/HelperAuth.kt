package com.trakclok.android.helpers

import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.trakclok.android.App

object HelperAuth {
    var auth: FirebaseAuth = Firebase.auth

    /**
     * check if user is signed in or not
     */
    fun loggedIn(): Boolean {
        return auth.currentUser != null
    }

    /**
     * logout from firebase & google
     */
    fun logout() {
        Identity.getSignInClient(App.context).signOut()
        auth.signOut()
    }

    /**
     * get logged in user
     */
    fun user(): FirebaseUser {
        return auth.currentUser!!
    }
}