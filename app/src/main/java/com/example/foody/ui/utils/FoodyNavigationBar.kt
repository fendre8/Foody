package com.example.foody.ui.utils

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController


@Composable
fun FoodyNavigationBar(navController: NavController) {
    var selectedItem by remember { mutableStateOf(0) }
    val navigationRoutesList = listOf(
        NavigationRoutes.Home,
        NavigationRoutes.Favourites,
        NavigationRoutes.About
    )
    NavigationBar {
        navigationRoutesList.forEachIndexed { index, item ->
            NavigationBarItem (
                icon = { Icon(item.icon, contentDescription = item.route) },
                label = { Text(item.route) },
                selected = selectedItem == index,
                onClick = {
                    if (selectedItem != index) {
                        selectedItem = index
                        navController.navigate(item.route)
                    }
                }
            )
        }
    }
}