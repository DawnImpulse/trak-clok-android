package com.trakclok.android.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Card
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trakclok.android.R
import com.trakclok.android.mapping.params.ParamsContentHomeHeader
import com.trakclok.android.mapping.params.ParamsContentProject
import com.trakclok.android.ui.container.CtButton
import com.trakclok.android.ui.container.CtIcon
import com.trakclok.android.ui.container.CtLottie
import com.trakclok.android.ui.content.ContentHomeHeader
import com.trakclok.android.ui.content.ContentProject
import com.trakclok.android.ui.item.ItemLottieRefresh
import com.trakclok.android.ui.item.ItemRaw
import com.trakclok.android.ui.layout.LayoutListReloadError
import com.trakclok.android.ui.other.sheet.SheetCtHome
import com.trakclok.android.ui.theme.TrakClokTheme
import com.trakclok.android.utils.F
import com.trakclok.android.utils.extension.showAnim
import com.trakclok.android.viewmodel.ViewModelHome
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun ViewHome(viewModelHome: ViewModelHome = viewModel()) {
    TrakClokTheme {
        SheetCtHome(
            state = viewModelHome.sheetState,
            sheet = viewModelHome.sheet,
            viewModelHome = viewModelHome
        ) {
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
}

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun ViewHomeListing(viewModel: ViewModelHome) {
    // --- scrollable list
    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        // --- scope
        val scope = rememberCoroutineScope()

        // --- header
        ContentHomeHeader(
            ParamsContentHomeHeader(
                day = F.currentDay(),
                month = F.currentMonth(),
                date = F.currentDate(),
                viewModel = viewModel
            )
        )

        // --- loading
        AnimatedVisibility(visible = viewModel.loading.value) {
            ItemLottieRefresh()
        }

        // --- error
        AnimatedVisibility(visible = viewModel.error.value != null) {
            LayoutListReloadError(error = viewModel.error.value.toString()) {
                viewModel.getProjects()
            }
        }

        // --- empty
        AnimatedVisibility(visible = viewModel.empty.value) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                // --- loading
                CtLottie(
                    itemRaw = ItemRaw(
                        light = R.raw.metasonic_light,
                        dark = R.raw.metasonic_dark
                    ), height = 400
                )

                // --- add
                Card(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        scope.launch {
                            viewModel.projectCreated.value = false
                            viewModel.startCurrentTime()
                            viewModel.sheetState.showAnim()
                        }
                    }
                ) {
                    Row(
                        Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CtIcon(
                            res = R.drawable.vd_plus,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "NEW PROJECT",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        // --- active projects
        AnimatedVisibility(
            visible = !viewModel.loading.value
                    && !viewModel.empty.value
                    && viewModel.error.value == null
                    && viewModel.activeProjects.value.isNotEmpty()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "ACTIVE",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleMedium,
                    letterSpacing = 2.sp,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                    fontWeight = FontWeight.Bold
                )

                Card(
                    Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = MaterialTheme.colorScheme.background,
                    elevation = 2.dp
                ) {
                    Column() {
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

        // --- inactive projects
        AnimatedVisibility(
            visible = !viewModel.loading.value
                    && !viewModel.empty.value
                    && viewModel.error.value == null
                    && viewModel.inactiveProjects.value.isNotEmpty()
        ) {
            Column(
                Modifier.padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "INACTIVE",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium,
                    letterSpacing = 2.sp,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                    fontWeight = FontWeight.Bold
                )

                Column(Modifier.padding(vertical = 16.dp)) {
                    // --- on each
                    viewModel.inactiveProjects.value.forEach {

                        // --- start timer
                        viewModel.startTimer(it)

                        // --- set content
                        Card(
                            Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
                            backgroundColor = MaterialTheme.colorScheme.background,
                            elevation = 0.dp
                        ) {
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

    }
}