package com.trakclok.android.ui.other.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.trakclok.android.mapping.params.ParamSheetNewProject
import com.trakclok.android.ui.theme.BLACK50
import com.trakclok.android.utils.Sheet
import com.trakclok.android.viewmodel.ViewModelHome

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun SheetCtHome(
    state: ModalBottomSheetState,
    sheet: MutableState<Sheet>,
    viewModelHome: ViewModelHome,
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = state,
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        scrimColor = BLACK50,
        sheetContent = {
            if (sheet.value == Sheet.NEW_PROJECT) SheetNewProject(
                ParamSheetNewProject(
                    state = state,
                    viewModel = viewModelHome
                )
            )
        },
        modifier = Modifier.background(color = Color.Black),
        content = content
    )
}