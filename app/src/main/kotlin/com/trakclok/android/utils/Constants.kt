package com.trakclok.android.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.trakclok.android.ui.theme.Capri
import com.trakclok.android.ui.theme.MacAndCheese
import com.trakclok.android.ui.theme.Sage
import com.trakclok.android.ui.theme.TiffanyBlue

const val NAME = "TrakClok"
const val DB_URL = ""

const val PAGE_SIZE = 50
val COLORS = listOf(MacAndCheese, TiffanyBlue, Sage, Capri)

enum class DarkMode { Dark, Light, System }
enum class ProjectType { Long, Short }
enum class Sheet { NEW_PROJECT }

object Errors {
    const val google_no_ui = 101
    const val google_intent = 102
    const val google_missing_token = 103
    const val google_firebase = 104
    const val google_ui_error = 105
}

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