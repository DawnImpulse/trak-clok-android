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
import com.trakclok.android.utils.extension.log
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

    // --- list of all times
    val listTime: MutableMap<String, MutableState<ObjectTime>> = mutableMapOf()

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