package com.trakclok.android.utils

const val NAME = "TrakClok"
const val DB_URL = ""

const val PAGE_SIZE = 50

enum class DarkMode { Dark, Light, System }
enum class ProjectType { Long, Short }
enum class Sheet { NEW_PROJECT }

val ProjectTypeDetails = mutableMapOf(
    Pair(
        ProjectType.Long,
        Pair(
            "Long Running",
            "These are tasks that you wish should be running for long time without pausing eg. sobriety, streak, etc"
        )
    ),
    Pair(
        ProjectType.Short,
        Pair(
            "Short Running",
            "Tasks that you wish to run continuously for shorter duration at a time & able to check total time spent on it eg.  project, job, cooking etc."
        )
    )
)