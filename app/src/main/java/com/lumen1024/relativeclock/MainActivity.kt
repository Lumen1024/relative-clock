package com.lumen1024.relativeclock

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lumen1024.relativeclock.screen.HomeScreen
import com.lumen1024.relativeclock.screen.Screen
import com.lumen1024.relativeclock.screen.SettingsScreen
import com.lumen1024.relativeclock.tools.getCurrentScreenAsState
import com.lumen1024.relativeclock.tools.getRelativeSlideInTransition
import com.lumen1024.relativeclock.tools.getRelativeSlideOutTransition
import com.lumen1024.relativeclock.ui.theme.RelativeClockTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RelativeClockTheme {
                AppContent(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun AppContent(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Surface(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .statusBarsPadding(),

            topBar = {
                AppTopBar(navController = navController)
            }

        ) { AppNavGraph(Modifier.padding(it),navController = navController) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(modifier: Modifier = Modifier, navController: NavHostController) {
    val currentScreen by navController.getCurrentScreenAsState()
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = if (currentScreen == Screen.Home)
                    stringResource(id = R.string.app_name)
                else
                    "Settings"
            )
        },
        actions = {
            if (currentScreen == Screen.Home)
                IconButton(onClick = { navController.navigate(Screen.Settings) }) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "settings")
                }
        },
        navigationIcon = {
            if (currentScreen == Screen.Settings)
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "settings")
                }
        }
    )
}

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home
    ) {
        composable<Screen.Home>(
            enterTransition = {
                getRelativeSlideInTransition(
                    right = listOf(Screen.Settings)
                )
            },
            exitTransition = {
                getRelativeSlideOutTransition(
                    right = listOf(Screen.Settings)
                )
            },
        ) { HomeScreen() }

        composable<Screen.Settings>(
            enterTransition = {
                getRelativeSlideInTransition(
                    left = listOf(Screen.Home),
                )
            },
            exitTransition = {
                getRelativeSlideOutTransition(
                    left = listOf(Screen.Home),
                )
            },
        ) { SettingsScreen() }
    }
}