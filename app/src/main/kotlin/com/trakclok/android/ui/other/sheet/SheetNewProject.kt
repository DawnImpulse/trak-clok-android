package com.trakclok.android.ui.other.sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.TextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsPadding
import com.trakclok.android.mapping.params.ParamSheetNewProject
import com.trakclok.android.mapping.preview.PreviewSheetState
import com.trakclok.android.ui.container.CtBox
import com.trakclok.android.utils.ProjectType
import com.trakclok.android.utils.ProjectTypeDetails

@Preview(widthDp = 400, showBackground = true)
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun SheetNewProject(@PreviewParameter(PreviewSheetState::class) params: ParamSheetNewProject) {

    // --- scope
    val scope = rememberCoroutineScope()

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


    }
}