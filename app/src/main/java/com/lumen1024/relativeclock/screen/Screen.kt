package com.lumen1024.relativeclock.screen

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable data object Home : Screen()
    @Serializable data object Settings : Screen()
}