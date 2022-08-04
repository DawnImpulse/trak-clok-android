package com.trakclok.android.database

import androidx.compose.ui.graphics.Color
import com.trakclok.android.helpers.HelperAuth
import com.trakclok.android.mapping.objects.ObjectProject
import com.trakclok.android.utils.Cfg
import com.trakclok.android.utils.ProjectType
import com.trakclok.android.utils.extension.toHexCode

object RealtimeProjects {

    /**
     * get list of projects for a user
     */
    suspend fun getList(callback: (error: Exception?, data: List<ObjectProject>?) -> Unit) {
        // --- get response from path
        RealtimeGeneric.change("/users/${HelperAuth.user().uid}/projects") { error, snapshot ->
            error?.let { callback(error, null) }
            snapshot?.let {
                // --- parse response
                val response = snapshot.children.map {
                    it.getValue(ObjectProject::class.java)!!
                }
                callback(null, response)
            }
        }
    }

    /**
     * create new project
     * @param name
     * @param time
     * @param type
     */
    suspend fun createProject(name: String, time: Long, type: ProjectType, color: Color): Boolean {
        val projectsPath = "/users/${HelperAuth.user().uid}/projects"
        val key = RealtimeGeneric.database.child(projectsPath).push().key

        if (key == null) throw Exception("couldn't create unique data; please try again")
        else return RealtimeGeneric.update(
            mapOf(
                "$projectsPath/$key" to ObjectProject(
                    id = key,
                    color = color.toHexCode(),
                    time = time,
                    splits = null,
                    active = true,
                    type = type.name,
                    name = name
                )
            )
        )
    }
}