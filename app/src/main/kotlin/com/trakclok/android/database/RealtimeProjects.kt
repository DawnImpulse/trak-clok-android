package com.trakclok.android.database

import com.trakclok.android.mapping.objects.ObjectProject
import com.trakclok.android.utils.Cfg
import com.trakclok.android.utils.ProjectType

object RealtimeProjects {

    /**
     * get list of projects for a user
     */
    suspend fun getList(callback: (error: Exception?, data: List<ObjectProject>?) -> Unit) {
        // --- get response from path
        Cfg.realtimeGeneric.change("/users/dsfd/projects") { error, snapshot ->
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
    suspend fun createProject(name: String, time: Long, type: ProjectType) =
        Cfg.realtimeGeneric.update(
            mapOf(
                Pair(
                    "/users/dsfd/projects/asdfe", ObjectProject(
                        id = "asdfe",
                        color = "#FF6663",
                        time = time,
                        splits = null,
                        active = true,
                        type = type.name,
                        name = name
                    )
                )
            )
        )
}