package com.trakclok.android.mapping.preview

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.trakclok.android.mapping.objects.ObjectProject
import com.trakclok.android.mapping.objects.ObjectTime
import com.trakclok.android.mapping.params.ParamsContentProject

@ExperimentalMaterialApi
class PreviewContentProject : PreviewParameterProvider<ParamsContentProject> {
    override val values = sequenceOf(
        ParamsContentProject(
            time = mutableStateOf(
                ObjectTime(
                    seconds = "20",
                    minutes = "34",
                    hours = "11",
                    days = "1",
                    months = "10",
                    years = "0"
                )
            ),
            project = ObjectProject(
                color = "#F98948",
                name = "Trak",
                type = "Short",
                id = "abcd",
                active = true,
                time = 1657609245692,
                splits = null
            )
        )
    )
}