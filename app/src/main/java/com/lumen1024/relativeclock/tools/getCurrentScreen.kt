package com.lumen1024.relativeclock.tools

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.lumen1024.relativeclock.screen.Screen

@Composable
fun NavHostController.getCurrentScreenAsState(): State<Screen?> {

    val state = this.currentBackStackEntryFlow
        .collectAsState(null)
    return remember {
        derivedStateOf {
            state.value?.destination?.getScreen()
        }
    }
}

fun NavDestination.getScreen() : Screen? {
    val screens = mapOf(
        Screen.Home.javaClass.canonicalName to Screen.Home,
        Screen.Settings.javaClass.canonicalName to Screen.Settings,
    )
    val route = this.route ?: return null
    return screens[route]
}