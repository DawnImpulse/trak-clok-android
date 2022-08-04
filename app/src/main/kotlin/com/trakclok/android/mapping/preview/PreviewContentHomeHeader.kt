package com.trakclok.android.mapping.preview

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.trakclok.android.mapping.params.ParamsContentHomeHeader
import com.trakclok.android.viewmodel.ViewModelHome

@ExperimentalMaterialApi
class PreviewContentHomeHeader : PreviewParameterProvider<ParamsContentHomeHeader> {
    override val values = sequenceOf(
        ParamsContentHomeHeader(
            date = "09",
            day = "Sunday",
            month = "April",
            viewModel = ViewModelHome()
        )
    )
}