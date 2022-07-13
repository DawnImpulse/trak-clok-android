package com.trakclok.android.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.trakclok.android.mapping.objects.ObjectHomeHeader
import com.trakclok.android.mapping.objects.ObjectProject
import com.trakclok.android.mapping.params.ParamsContentProject
import com.trakclok.android.ui.container.CtLazy
import com.trakclok.android.ui.content.ContentHomeHeader
import com.trakclok.android.ui.content.ContentProject
import com.trakclok.android.ui.item.ItemLottieRefresh
import com.trakclok.android.ui.layout.LayoutSwipeRefresh
import com.trakclok.android.ui.theme.TrakClokTheme
import com.trakclok.android.utils.extension.log
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

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun ViewHomeListing(viewModel: ViewModelHome) {
    // --- scrollable list
    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        // --- header
        ContentHomeHeader()

        // --- loading
        AnimatedVisibility(visible = viewModel.loading.value) {
            ItemLottieRefresh()
        }

        // --- list
        AnimatedVisibility(visible = !viewModel.loading.value && viewModel.error.value == null && !viewModel.empty.value) {
            // --- card
            Card(
                Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                // --- on each
                viewModel.activeProjects.value.forEach {

                    // --- start timer
                    viewModel.startTimer(it)

                    // --- set content
                    Box(Modifier.padding(horizontal = 12.dp, vertical = 24.dp)) {
                        ContentProject(
                            params = ParamsContentProject(
                                time = viewModel.listTime[it.id]!!,
                                project = it
                            ),
                        )
                    }
                }
            }
        }

    }
}