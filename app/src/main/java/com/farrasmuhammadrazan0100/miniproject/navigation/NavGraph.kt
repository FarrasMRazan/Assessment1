package com.farrasmuhammadrazan0100.miniproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.farrasmuhammadrazan0100.miniproject.ui.screen.AboutScreen
import com.farrasmuhammadrazan0100.miniproject.ui.screen.MainScreen
import com.farrasmuhammadrazan0100.miniproject.ui.screen.Screen

@Composable
fun SetupNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route){
            MainScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
    }
}