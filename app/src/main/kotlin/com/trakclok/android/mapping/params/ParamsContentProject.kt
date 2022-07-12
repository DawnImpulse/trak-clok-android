package com.trakclok.android.mapping.params

import androidx.compose.runtime.MutableState
import com.trakclok.android.mapping.objects.ObjectProject
import com.trakclok.android.mapping.objects.ObjectTime

data class ParamsContentProject(
    val time: MutableState<ObjectTime>,
    val project: ObjectProject
)