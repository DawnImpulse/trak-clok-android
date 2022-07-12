package com.trakclok.android.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.trakclok.android.database.data.DataHome
import kotlinx.coroutines.flow.Flow

class ViewModelHome : ViewModel() {

    // --- refresh status
    val isRefreshing = mutableStateOf(false)

    // --- get all projects
    var projects: Flow<PagingData<Any>> =
        Pager(PagingConfig(50)) { DataHome() }.flow.cachedIn(viewModelScope)
}