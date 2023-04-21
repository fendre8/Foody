package com.example.foody.model

data class Recipe(
    val id: Number,
    val apiId: Number,
    val mealName: String,
    val category: String,
    val area: String,
    val instructions: String,
    val mealThumb: String,
    val tags: String,
    val youtubeLink: String,
    val ingredients: List<Ingredient>
)
