package com.trakclok.android.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.internal.common.CrashlyticsCore
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.trakclok.android.database.RealtimeProjects
import com.trakclok.android.mapping.objects.ObjectProject
import com.trakclok.android.mapping.objects.ObjectTime
import com.trakclok.android.utils.F
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewModelHome : ViewModel() {

    // --- projects
    val activeProjects: MutableState<List<ObjectProject>> = mutableStateOf(listOf())
    val inactiveProjects: MutableState<List<ObjectProject>> = mutableStateOf(listOf())

    // --- loading & error
    val loading = mutableStateOf(true)
    val error: MutableState<String?> = mutableStateOf(null)
    val empty = mutableStateOf(false)

    // --- refresh status
    val isRefreshing = mutableStateOf(false)

    // --- list of all times
    val listTime: MutableMap<String, MutableState<ObjectTime>> = mutableMapOf()

    // --- get projects on init
    init {
        getProjects()
    }

    // --- get projects
    private fun getProjects() {

        // --- clear projects list
        loading.value = true
        error.value = null

        // --- launch scope
        viewModelScope.launch {
            try {
                val list = RealtimeProjects.getList()
                val active = mutableListOf<ObjectProject>()
                val inactive = mutableListOf<ObjectProject>()

                // --- create list of active & inactive
                list.forEach {
                    if (it.active) active.add(it)
                    else inactive.add(it)
                }

                // --- check if empty
                empty.value = active.isEmpty() && inactive.isEmpty()
                loading.value = false

                // --- set lists
                activeProjects.value = active
                inactiveProjects.value = inactive
            }
            // --- exception
            catch (e: Exception) {
                error.value = e.message
                loading.value = false
                e.printStackTrace()
                Firebase.crashlytics.recordException(e)
            }
        }
    }

    /**
     * start timer
     * @param project
     */
    fun startTimer(project: ObjectProject) {
        // --- run first time blocking
        listTime[project.id] = mutableStateOf(F.parseLongTime(project.time))

        // --- then run on bg (only if active)
        if (project.active)
            viewModelScope.launch {
                // --- run non stop
                while (true) {
                    // --- wait 1 second before continuing
                    delay(1000)

                    // --- get time object and set
                    listTime[project.id]?.value = F.parseLongTime(project.time)
                }
            }
    }
}