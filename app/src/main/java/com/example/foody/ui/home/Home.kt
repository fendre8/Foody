package com.example.foody.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.foody.model.Recipe
import com.example.foody.ui.main.MainViewModel
import com.example.foody.ui.recipes.RecipeItems

@Composable
fun Home(
    viewModel: MainViewModel,
    showFavourites: Boolean = false,
    selectRecipe: (Int) -> Unit,
) {
    val recipes: List<Recipe> = if (showFavourites) {
        viewModel.favouriteMeals.collectAsState(initial = listOf()).value
    } else {
        viewModel.meals.value
    }
    val isLoading: Boolean by viewModel.isLoading

    RecipeItems(
        recipes,
        markRecipeAsFavourite = { viewModel.addFavouriteMeal(it) },
        onItemClick = selectRecipe,
        favourites = showFavourites
        )
    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            CircularProgressIndicator()
        }
    }
}