package com.trakclok.android.mapping.objects

import androidx.compose.ui.text.toUpperCase
import com.google.firebase.database.DataSnapshot
import com.trakclok.android.utils.ProjectType

data class ObjectProject(
    val id: String,
    val name: String,
    val color: String,
    val time: Long,
    val active: Boolean,
    val type: String,
    val splits: Map<String, ObjectProjectSplit>?
){
    fun typeOf(): ProjectType{
        return ProjectType.valueOf(type)
    }
}

data class ObjectProjectSplit(
    val from: Long,
    val to: Long
)