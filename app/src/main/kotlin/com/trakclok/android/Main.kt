package com.trakclok.android

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.trakclok.android.ui.view.ViewHome
import com.trakclok.android.utils.Cfg
import com.trakclok.android.utils.Route

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun Main(navController: NavHostController) {
    Cfg.navigation = navController
    NavHost(navController = navController, startDestination = Route.Home.label) {
        // --- home screen
        composable(Route.Home.label) { ViewHome() }


    }
}