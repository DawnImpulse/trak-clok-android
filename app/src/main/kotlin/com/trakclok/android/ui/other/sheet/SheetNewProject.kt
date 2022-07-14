package com.trakclok.android.ui.other.sheet

import androidx.compose.foundation.layout.*
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
import com.google.accompanist.insets.navigationBarsPadding
import com.trakclok.android.mapping.params.ParamSheetNewProject
import com.trakclok.android.mapping.preview.PreviewSheetState

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
            .padding(start = 24.dp, end = 24.dp)
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
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )
    }
}