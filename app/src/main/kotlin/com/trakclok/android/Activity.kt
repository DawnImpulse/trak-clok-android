package com.trakclok.android

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.orhanobut.hawk.Hawk
import com.trakclok.android.ui.theme.TrakClokTheme
import com.trakclok.android.utils.Cfg

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
class Activity : AppCompatActivity() {
    // on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- set edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // --- window inset
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return

        // --- configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // --- hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        // --- set content
        setContent {
            val navigation = rememberNavController()

            // provide insets
            ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                Main(navController = navigation)
            }
        }
    }

    // attached to window
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // get camera cutout safe top inset
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Cfg.topInset = window.decorView.rootWindowInsets?.displayCutout?.safeInsetTop ?: 1
        }
    }
}