package com.trakclok.android

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.trakclok.android.helpers.HelperAuth
import com.trakclok.android.ui.view.ViewAuth
import com.trakclok.android.ui.view.ViewHome
import com.trakclok.android.utils.Cfg
import com.trakclok.android.utils.Route

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun Main(navController: NavHostController) {

    // --- initialize controller
    Cfg.navigation = navController

    // --- start destination
    val start = if (HelperAuth.loggedIn()) Route.Home.label else Route.Auth.label

    NavHost(navController = navController, startDestination = start) {
        // --- home screen
        composable(Route.Home.label) { ViewHome() }

        // --- auth screen
        composable(Route.Auth.label) { ViewAuth() }

    }
}