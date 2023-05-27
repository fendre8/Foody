package com.example.foody.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationRoutes(val route: String, val icon: ImageVector) {
    object Home : NavigationRoutes("Home", Icons.Outlined.Home)
    object Favourites : NavigationRoutes("Favourite", Icons.Outlined.StarBorder)
    object About : NavigationRoutes("About", Icons.Outlined.Info)
    object Details : NavigationRoutes("Details", Icons.Outlined.Info) {
        const val routeWithArgument: String = "Details/{mealId}"
        const val argument: String = "mealId"
    }
}
