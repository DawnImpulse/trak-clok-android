package com.trakclok.android.mapping.params

import androidx.compose.material.ExperimentalMaterialApi
import com.trakclok.android.viewmodel.ViewModelHome

@ExperimentalMaterialApi
data class ParamsContentHomeHeader(
    val date: String,
    val month: String,
    val day: String,
    val viewModel: ViewModelHome
)