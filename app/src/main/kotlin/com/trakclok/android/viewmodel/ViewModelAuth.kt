package com.trakclok.android.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ViewModelAuth : ViewModel() {
    val loading = mutableStateOf(false)

    /**
     * start google login
     */
    fun startLogin() {
        loading.value = true
    }
}