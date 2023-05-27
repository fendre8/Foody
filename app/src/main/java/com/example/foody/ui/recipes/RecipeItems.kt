package com.example.foody.ui.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.foody.model.Ingredient
import com.example.foody.model.Recipe

@Composable
fun RecipeItems(
    recipes: List<Recipe>,
    markRecipeAsFavourite: (Int) -> Unit = {},
    onItemClick: (Int) -> Unit = {},
    favourites: Boolean = false
) {
    ConstraintLayout() {
        LazyColumn()
        {
            item {
                recipes.forEach { recipe ->
                    RecipeItem(
                        recipe,
                        markRecipeAsFavourite,
                        onItemClick,
                        favourites
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
fun RecipeItem(
    recipe: Recipe,
    markRecipeAsFavourite: (Int) -> Unit = {},
    onItemClick: (Int) -> Unit = {},
    favourites: Boolean = false
) {
    var isItemFavourite by rememberSaveable { mutableStateOf(false) }

    ListItem(
        tonalElevation = 2.dp,
        headlineContent = { Text(recipe.mealName) },
        supportingContent = {
            AssistChip(
                onClick = { /*TODO*/ },
                label = { Text(recipe.category) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    labelColor = Color.White
                )
            )
        },
        trailingContent = {
            FilledTonalIconButton(onClick = {
                markRecipeAsFavourite(recipe.apiId)
                isItemFavourite = true
            }) {
                Icon(
                    imageVector = if (isItemFavourite || favourites) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favourite",
                )
            }
        },
        leadingContent = {
            Image(
                painter = rememberAsyncImagePainter(recipe.mealThumb),
                contentDescription = recipe.mealName,
                modifier = Modifier.size(128.dp)
            )
        },
        modifier = Modifier.clickable {
            onItemClick(recipe.apiId)
        }
    )
}


val testRecipe: Recipe = Recipe(
    apiId = 52771,
    area = "Italian",
    category = "Vegetarian",
    id = 1,
    ingredients = listOf<Ingredient>(),
    instructions = "Bring a large pot of water to a boil. Add kosher salt to the boiling water, then add the pasta. Cook according to the package instructions, about 9 minutes.\n" +
            "In a large skillet over medium-high heat, add the olive oil and heat until the oil starts to shimmer. Add the garlic and cook, stirring, until fragrant, 1 to 2 minutes. Add the chopped tomatoes, red chile flakes, Italian seasoning and salt and pepper to taste. Bring to a boil and cook for 5 minutes. Remove from the heat and add the chopped basil.\n" +
            "Drain the pasta and add it to the sauce. Garnish with Parmigiano-Reggiano flakes and more basil and serve warm.",
    mealName = "Spicy Arrabiata Penne",
    mealThumb = "https://www.themealdb.com/images/media/meals/ustsqw1468250014.jpg",
    tags = "Pasta,Curry",
    youtubeLink = "https://www.youtube.com/watch?v=1IszT_guI08"
)
val testRecipes = List(20) { testRecipe }

@Composable
fun RecipeItemPreview() {
    RecipeItem(recipe = testRecipe)
}

@Preview
@Composable
fun RecipeItemsPreview() {
    RecipeItems(recipes = testRecipes)
}