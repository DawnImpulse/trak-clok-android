package com.trakclok.android.ui.other.sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.TextField
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.insets.navigationBarsPadding
import com.trakclok.android.R
import com.trakclok.android.mapping.params.ParamSheetNewProject
import com.trakclok.android.mapping.preview.PreviewSheetState
import com.trakclok.android.ui.container.CtBox
import com.trakclok.android.ui.container.CtIcon
import com.trakclok.android.utils.ProjectType
import com.trakclok.android.utils.ProjectTypeDetails
import com.trakclok.android.utils.extension.toMilli
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogButtons
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Preview(widthDp = 400, showBackground = true)
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun SheetNewProject(@PreviewParameter(PreviewSheetState::class) params: ParamSheetNewProject) {

    // --- scope
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val dateDialog = rememberMaterialDialogState()
    val timeDialog = rememberMaterialDialogState()

    // --- start active time
    params.viewModel.startCurrentTime()

    // --- date picker dialog
    MaterialDialog(
        dialogState = dateDialog,
        buttons = {
            positiveButton(
                "NEXT",
                TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                onClick = { timeDialog.show() })
            negativeButton(
                "DISMISS",
                TextStyle(color = MaterialTheme.colorScheme.surfaceVariant, letterSpacing = 1.sp)
            )
        },
        backgroundColor = MaterialTheme.colorScheme.surface,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
        shape = RoundedCornerShape(16.dp),
    ) {
        datepicker(
            colors = DatePickerDefaults.colors(
                dateActiveBackgroundColor = MaterialTheme.colorScheme.primary,
                dateActiveTextColor = MaterialTheme.colorScheme.onPrimary,
                headerBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                calendarHeaderTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                headerTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                dateInactiveTextColor = MaterialTheme.colorScheme.onSurface,
                dateInactiveBackgroundColor = MaterialTheme.colorScheme.surface
            ),
            waitForPositiveButton = true,
            allowedDateValidator = {
                it.toMilli() <= System.currentTimeMillis()
            }
        ) {
            params.viewModel.setCurrentFromLocalDate(it)
        }
    }

    // --- time picker dialog
    MaterialDialog(
        dialogState = timeDialog,
        buttons = {
            positiveButton(
                "DONE",
                TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
            )
            negativeButton(
                "DISMISS",
                TextStyle(color = MaterialTheme.colorScheme.surfaceVariant, letterSpacing = 1.sp)
            )
        },
        backgroundColor = MaterialTheme.colorScheme.surface,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
        shape = RoundedCornerShape(12.dp),
    ) {
        timepicker(
            colors = TimePickerDefaults.colors(
                headerTextColor = MaterialTheme.colorScheme.primary,
                activeBackgroundColor = MaterialTheme.colorScheme.primary,
                activeTextColor = MaterialTheme.colorScheme.onPrimary,
                selectorColor = MaterialTheme.colorScheme.primary,
                selectorTextColor = MaterialTheme.colorScheme.onPrimary,
                borderColor = MaterialTheme.colorScheme.primary,
                inactiveBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                inactiveTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                inactivePeriodBackground = MaterialTheme.colorScheme.surface
            ),
            waitForPositiveButton = true,
        ) {
            params.viewModel.setCurrentFromLocalTime(it)
        }
    }

    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
    ) {

        // --- kink
        Surface(
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp)
                .width(48.dp)
                .height(6.dp),
            shape = RoundedCornerShape(4.dp)
        ) {}

        // --- name
        val name by remember { params.viewModel.projectName }
        OutlinedTextField(
            value = name,
            onValueChange = { params.viewModel.projectName.value = it },
            label = { Text(text = "Project Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 24.dp, end = 20.dp, bottom = 24.dp),
            shape = RoundedCornerShape(16.dp)
        )

        // --- type of project
        ProjectTypeDetails.forEach {
            Card(
                onClick = {
                    params.viewModel.projectType.value = it.key
                },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
            ) {
                // --- if this item is selected or not
                val selected = it.key == params.viewModel.projectType.value

                Column(Modifier.padding(vertical = 8.dp, horizontal = 12.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Surface(
                            Modifier.size(20.dp),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(
                                2.dp,
                                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
                            )
                        ) {
                            AnimatedVisibility(visible = selected) {
                                Box(Modifier.fillMaxSize()) {
                                    Surface(
                                        Modifier
                                            .size(10.dp)
                                            .align(Alignment.Center),
                                        shape = RoundedCornerShape(10.dp),
                                        color = MaterialTheme.colorScheme.primary
                                    ) {

                                    }
                                }
                            }
                        }

                        Text(
                            text = it.value.first,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 16.dp),
                            letterSpacing = 1.sp,
                            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
                        )
                    }

                    Text(
                        text = it.value.second,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
        }

        // --- time text
        Text(
            text = "Starting From",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 54.dp, top = 32.dp),
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        // --- current time card
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
            modifier = Modifier.padding(16.dp),
            onClick = { dateDialog.show() }
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(84.dp)
            ) {
                Row(
                    Modifier
                        .padding(start = 36.dp)
                        .align(Alignment.CenterStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CtIcon(
                        res = R.drawable.vd_clock,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )

                    Text(
                        text = params.viewModel.currentTime.value.first,
                        style = MaterialTheme.typography.titleMedium,
                        letterSpacing = 1.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier.padding(start = 24.dp)
                    )
                }
            }
        }

        // --- add
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 36.dp, end = 16.dp, bottom = 24.dp)
        ) {
            Card(
                Modifier
                    .align(Alignment.CenterEnd)
                    .clickable(
                        onClick = {

                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            bounded = true,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    ),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

                ) {
                Box(
                    Modifier
                        .width(124.dp)
                        .height(48.dp)
                ) {
                    Row(Modifier.align(Alignment.Center)) {
                        CtIcon(
                            res = R.drawable.vd_check_square,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "ADD",
                            style = MaterialTheme.typography.titleMedium,
                            letterSpacing = 1.5.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                }
            }
        }

    }
}