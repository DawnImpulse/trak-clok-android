package com.trakclok.android.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.trakclok.android.database.data.DataHome
import com.trakclok.android.mapping.objects.ObjectProject
import com.trakclok.android.mapping.objects.ObjectTime
import com.trakclok.android.utils.F
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ViewModelHome : ViewModel() {

    // --- refresh status
    val isRefreshing = mutableStateOf(false)

    // --- get all projects
    var projects: Flow<PagingData<Any>> =
        Pager(PagingConfig(50)) { DataHome() }.flow.cachedIn(viewModelScope)

    // --- time job
    private lateinit var timeJob: Job

    // --- list of all times
    private val listTime: MutableMap<String, MutableState<ObjectTime>> = mutableMapOf()

    /**
     * start active timer on all projects
     * @param projects
     */
    fun startTimers(projects: LazyPagingItems<Any>) {
        timeJob = viewModelScope.launch {
            if (projects.itemCount > 0)
                while (true) {
                    val count = projects.itemCount
                    for (c in count downTo 1) {

                        // --- get data in items
                        val data = projects[c - 1]

                        // --- check if data is of type project
                        if (data is ObjectProject) {
                            // --- get time object
                            val timeObj = F.parseLongTime(data.time)

                            // --- if map is initialized then update value
                            if (listTime.isNotEmpty())
                                listTime[data.id]?.value = timeObj

                            // --- else create new value
                            else
                                listTime[data.id] = mutableStateOf(timeObj)
                        }
                    }

                    // --- 1 second delay
                    delay(1000)
                }
        }
    }

    /**
     * stop active timer job
     */
    fun stopTimers() {
        if (::timeJob.isInitialized && timeJob.isActive) timeJob.cancel()
    }

    fun startTimer() {

    }
}