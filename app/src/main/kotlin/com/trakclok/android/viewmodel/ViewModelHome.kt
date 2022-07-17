package com.trakclok.android.viewmodel

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.trakclok.android.database.RealtimeProjects
import com.trakclok.android.mapping.objects.ObjectProject
import com.trakclok.android.mapping.objects.ObjectTime
import com.trakclok.android.utils.F
import com.trakclok.android.utils.ProjectType
import com.trakclok.android.utils.Sheet
import com.trakclok.android.utils.extension.log
import com.trakclok.android.utils.extension.toLocalDate
import com.trakclok.android.utils.extension.toMilli
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.TemporalQueries.localDate


@ExperimentalMaterialApi
class ViewModelHome() : ViewModel() {
    // --- current system time
    val currentTime: MutableState<Pair<String, Long>> =
        mutableStateOf(Pair("21 Mar ‘22, 11:20:00", 1))

    // --- current time job
    lateinit var currentTimeJob: Job

    // --- current time set by user
    val currentTimeUser = mutableStateOf(false)

    // --- projects
    val activeProjects: MutableState<List<ObjectProject>> = mutableStateOf(listOf())
    val inactiveProjects: MutableState<List<ObjectProject>> = mutableStateOf(listOf())

    // --- state of sheet
    val sheetState: ModalBottomSheetState =
        ModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmStateChange = { true }
        )

    // ---- sheet to show
    val sheet = mutableStateOf(Sheet.NEW_PROJECT)

    // --- loading, error & empty
    val loading = mutableStateOf(true)
    val error: MutableState<String?> = mutableStateOf(null)
    val empty = mutableStateOf(false)

    // --- new project
    val projectName = mutableStateOf("")
    val projectTime = mutableStateOf(System.currentTimeMillis())
    val projectType = mutableStateOf(ProjectType.Short)

    // --- refresh status
    val isRefreshing = mutableStateOf(false)

    // --- list of all times
    val listTime: MutableMap<String, MutableState<ObjectTime>> = mutableMapOf()

    // --- get projects on init
    init {
        getProjects()
    }

    // --- get projects
    fun getProjects() {

        // --- clear projects list
        loading.value = true
        error.value = null
        empty.value = false

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

    /**
     * start current time
     */
    fun startCurrentTime() {
        if (!currentTimeUser.value)
            currentTimeJob = viewModelScope.launch {
                // --- run non stop
                while (true) {
                    // --- wait 1 second before continuing
                    delay(1000)
                    log.d("here")

                    // --- get time object and set
                    val time = System.currentTimeMillis()
                    currentTime.value = Pair(F.milliToCurrentDate(time), time)
                }
            }
    }

    /**
     * stop current time
     */
    private fun stopCurrentTime() {
        if (::currentTimeJob.isInitialized && currentTimeJob.isActive) currentTimeJob.cancel()
    }

    /**
     * set current time from local date
     * @param date
     */
    fun setCurrentFromLocalDate(date: LocalDate) {
        // --- stop change active time
        stopCurrentTime()

        // --- convert to milli
        val timeInMillis = date.toMilli()

        // --- set current time
        currentTime.value = Pair(F.milliToCurrentDate(timeInMillis), timeInMillis)
    }

    /**
     * set current time from current time using previous time
     * @param time
     */
    fun setCurrentFromLocalTime(time: LocalTime) {
        // --- convert current time to local date
        val date = currentTime.value.second.toLocalDate()

        // --- set new time on date
        val dateTime = date.atTime(time)

        // --- set current time
        val milli = dateTime.toMilli()
        currentTime.value = Pair(F.milliToCurrentDate(milli), milli)
    }
}