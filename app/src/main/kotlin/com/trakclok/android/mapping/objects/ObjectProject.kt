package com.trakclok.android.mapping.objects

import androidx.compose.ui.text.toUpperCase
import com.google.firebase.database.DataSnapshot
import com.trakclok.android.utils.ProjectType

data class ObjectProject(
    val id: String = "id",
    val name: String = "nothing",
    val color: String = "#FFFFFF",
    val time: Long = 12345643211,
    val active: Boolean = false,
    val type: String = "Short",
    val splits: Map<String, ObjectProjectSplit>? = null
){
    fun typeOf(): ProjectType{
        return ProjectType.valueOf(type)
    }
}

data class ObjectProjectSplit(
    val from: Long,
    val to: Long
)