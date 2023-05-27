package com.example.foody.ui.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foody.ui.about.About
import com.example.foody.ui.details.Details
import com.example.foody.ui.home.Home
import com.example.foody.ui.utils.FoodyNavigationBar
import com.example.foody.ui.utils.FoodySearchBar
import com.example.foody.ui.utils.NavigationRoutes

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val navController = rememberNavController()

    val searchTextState by viewModel.searchTextState.collectAsState()
    var title by remember { mutableStateOf(NavigationRoutes.Home.route) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute != NavigationRoutes.Details.routeWithArgument) {
                MainAppBar(
                    title = title,
                    searchTextState = searchTextState,
                    onTextChange = {
                        viewModel.onSearchTextChange(it)
                    },
                    onSetCategory = {
                        viewModel.onSetCategory(it)
                    },
                    onSearchClicked = {
                        Log.d("Searched text", it)
                    },
                    isHome = currentRoute == NavigationRoutes.Home.route,
                    mainViewModel = viewModel
                )
            }
        },
        bottomBar = { FoodyNavigationBar(navController) },
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Button(onClick = {
                throw RuntimeException("Test Crash") // Force a crash
            }) { Text("Crash", color = Color.Black) }
            FoodyNavHost(
                navController = navController,
                mainViewModel = viewModel,
                onTitleChange = { title = it })
        }
    }
}

@Composable
fun FoodyNavHost(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    onTitleChange: (String) -> Unit
) {
    NavHost(navController = navController, startDestination = NavigationRoutes.Home.route) {
        composable(NavigationRoutes.Home.route) {
            onTitleChange(NavigationRoutes.Home.route)
            Home(mainViewModel, selectRecipe = {
                val route = "${NavigationRoutes.Details.route}/$it"
                navController.navigate(route)
            })
        }
        composable(NavigationRoutes.Favourites.route) {
            onTitleChange(NavigationRoutes.Favourites.route)
            Home(mainViewModel, showFavourites = true, selectRecipe = {
                val route = "${NavigationRoutes.Details.route}/$it"
                navController.navigate(route)
            })
        }
        composable(NavigationRoutes.About.route) {
            onTitleChange(NavigationRoutes.About.route)
            About()
        }
        composable(
            route = NavigationRoutes.Details.routeWithArgument,
            arguments = listOf(navArgument(NavigationRoutes.Details.argument) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getInt(NavigationRoutes.Details.argument)
                ?: return@composable

            onTitleChange(NavigationRoutes.Details.route)
            Details(itemId = mealId, viewModel = hiltViewModel(), navBack = {
                navController.popBackStack()
            })
        }
    }
}

@Composable
fun MainAppBar(
    title: String,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onSetCategory: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    isHome: Boolean = true,
    mainViewModel: MainViewModel
) {
    FoodySearchBar(
        title = title,
        text = searchTextState,
        onTextChanged = onTextChange,
        onSetCategory = onSetCategory,
        onSearchClicked = onSearchClicked,
        isHome = isHome,
        mainViewModel = mainViewModel
    )
}