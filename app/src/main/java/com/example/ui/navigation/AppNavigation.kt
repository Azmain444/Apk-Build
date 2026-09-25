package com.example.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ToolsScreen
import com.example.ui.screens.CameraScreen
import com.example.ui.screens.AboutScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val items = listOf(
        Pair("home", "Home"),
        Pair("tools", "Tools"),
        Pair("camera", "Camera"),
        Pair("about", "About")
    )
    val icons = listOf(Icons.Default.Home, Icons.Default.Build, Icons.Default.Camera, Icons.Default.Info)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item.second) },
                        label = { Text(item.second) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.first } == true,
                        onClick = {
                            navController.navigate(item.first) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "home", modifier = Modifier.padding(innerPadding)) {
            composable("home") { HomeScreen(navController) }
            composable("tools") { ToolsScreen(navController) }
            composable("camera") { CameraScreen(navController) }
            composable("about") { AboutScreen(navController) }
        }
    }
}
