package com.example.foody.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(
    @PrimaryKey val id: Number,
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
