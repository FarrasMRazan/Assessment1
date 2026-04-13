package com.farrasmuhammadrazan0100.miniproject.ui.screen

sealed class Screen(val route: String) {
    data object Home : Screen("mainScreen")
    data object About : Screen("aboutScreen")
}