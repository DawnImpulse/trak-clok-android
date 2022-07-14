package com.trakclok.android.mapping.params

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import com.trakclok.android.viewmodel.ViewModelHome

@ExperimentalMaterialApi
data class ParamSheetNewProject(
    val state: ModalBottomSheetState,
    val viewModel: ViewModelHome
)