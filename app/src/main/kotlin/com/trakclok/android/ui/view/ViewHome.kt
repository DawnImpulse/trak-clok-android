package com.trakclok.android.ui.view

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.trakclok.android.mapping.objects.ObjectHomeHeader
import com.trakclok.android.ui.container.CtLazy
import com.trakclok.android.ui.content.ContentHomeHeader
import com.trakclok.android.ui.item.ItemLottieRefresh
import com.trakclok.android.ui.layout.LayoutSwipeRefresh
import com.trakclok.android.ui.theme.TrakClokTheme
import com.trakclok.android.viewmodel.ViewModelHome
import kotlin.coroutines.coroutineContext

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun ViewHome(viewModelHome: ViewModelHome = viewModel()) {
    TrakClokTheme {
        Scaffold(
            floatingActionButton = { },
            bottomBar = { },
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            it.calculateBottomPadding()
            ViewHomeListing(viewModel = viewModelHome)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ViewHomeListing(viewModel: ViewModelHome) {
    val projects = viewModel.projects.collectAsLazyPagingItems()

    // swipe refresh
    LayoutSwipeRefresh(
        isRefreshing = viewModel.isRefreshing.value,
        onRefresh = {
            viewModel.isRefreshing.value = true
            projects.refresh()
        }
    ) {

        // --- start timers
        DisposableEffect(projects){
            viewModel.startTimers(projects)
            onDispose { viewModel.stopTimers() }
        }

        // --- lazy column
        CtLazy(
            data = projects,

            // --- actual item to show
            item = { it, _ ->
                if (it is ObjectHomeHeader) ContentHomeHeader(
                    date = it.date,
                    day = it.day,
                    month = it.month
                )
                else {

                }
            },

            // --- on refresh
            refresh = {
                viewModel.isRefreshing.value = false
                item { ItemLottieRefresh() }
            },
        )
    }
}