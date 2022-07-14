package com.trakclok.android.mapping.preview

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.trakclok.android.mapping.params.ParamSheetNewProject
import com.trakclok.android.viewmodel.ViewModelHome

@ExperimentalMaterialApi
class PreviewSheetState : PreviewParameterProvider<ParamSheetNewProject> {
    override val values = sequenceOf(
        ParamSheetNewProject(
            state = ModalBottomSheetState(ModalBottomSheetValue.Hidden),
            viewModel = ViewModelHome()
        )
    )
}