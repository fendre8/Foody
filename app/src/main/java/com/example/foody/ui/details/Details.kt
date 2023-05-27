package com.example.foody.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRow
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.example.foody.model.Ingredient
import com.example.foody.model.Recipe

@Composable
fun Details(
    itemId: Int,
    viewModel: DetailsViewModel,
    navBack: () -> Unit = {}
) {
    LaunchedEffect(key1 = itemId) {
        viewModel.loadMealById(itemId)
    }
    val recipe: Recipe? by viewModel.mealDetailsFlow.collectAsState(initial = null)
    recipe?.let { recipe ->
        MealDetails(
            recipe,
            navBack = navBack,
            saveRecipe = { instructions: String, ingredients: Array<Ingredient> ->
                viewModel.saveRecipe(
                    recipe.copy(
                        instructions = instructions,
                        ingredients = ingredients.toList()
                    )
                )
            })
    }
}

@Composable
fun MealDetails(
    recipe: Recipe,
    navBack: () -> Unit = {},
    saveRecipe: (String, Array<Ingredient>) -> Unit
) {
    val titles = listOf("Ingredients", "Instructions")
    var tabState by remember { mutableStateOf(0) }
    var isEditing by remember { mutableStateOf(false) }
    var instructions by remember { mutableStateOf(recipe.instructions) }
    val ingredients = remember { mutableStateListOf(*recipe.ingredients.toTypedArray()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box() {
            Text(
                text = recipe.mealName,
                fontWeight = FontWeight(400),
                color = Color.White,
                fontSize = 35.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .zIndex(100F)
                    .padding(10.dp)
            )
            FilledIconButton(
                onClick = { navBack() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .zIndex(100F)
                    .padding(10.dp)

            ) {
                Icon(
                    Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(32.dp)
                )
            }
            if (isEditing) {
                Column(
                    Modifier
                        .align(Alignment.TopEnd)
                        .zIndex(100F)
                        .padding(10.dp)
                ) {
                    FilledIconButton(
                        onClick = { isEditing = false }
                    ) {
                        Icon(
                            Icons.Outlined.Close,
                            contentDescription = "Close",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    FilledIconButton(
                        onClick = {
                            isEditing = false
                            saveRecipe(
                                instructions,
                                ingredients.toTypedArray()
                            )
                        }
                    ) {
                        Icon(
                            Icons.Outlined.Check,
                            contentDescription = "Save",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            } else {
                FilledIconButton(
                    onClick = { isEditing = true },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .zIndex(100F)
                        .padding(10.dp)

                ) {
                    Icon(
                        Icons.Outlined.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            Image(
                painter = rememberAsyncImagePainter(recipe.mealThumb),
                contentDescription = recipe.mealName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
        }
        TabRow(selectedTabIndex = tabState) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = tabState == index,
                    onClick = { tabState = index },
                    text = { Text(text = title, fontSize = 20.sp) },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Black,
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            if (tabState == 0) {
                ingredients.forEachIndexed { index, ingredient ->
                    if (isEditing) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextField(
                                value = ingredient.name,
                                onValueChange = { ingredients[index] = ingredient.copy(name = it) })
                            TextField(
                                value = ingredient.measure,
                                onValueChange = {
                                    ingredients[index] = ingredient.copy(measure = it)
                                })
                        }
                    } else {
                        ListItem(
                            headlineContent = { Text(ingredient.name) },
                            trailingContent = { Text(ingredient.measure) }
                        )
                    }
                    Divider()
                }
            }
            if (tabState == 1) {
                TextField(
                    value = instructions,
                    onValueChange = { instructions = it },
                    readOnly = !isEditing,
                    singleLine = false,
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                    )
                )
            }
        }
    }
}