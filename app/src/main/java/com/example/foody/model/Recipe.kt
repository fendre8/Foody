package com.example.foody.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.foody.converters.IngredientsTypeConverter

@Entity
@TypeConverters(IngredientsTypeConverter::class)
data class Recipe(
    @PrimaryKey val id: Int?,
    val apiId: Int,
    val mealName: String,
    var category: String,
    val area: String,
    var instructions: String,
    val mealThumb: String,
    val tags: String?,
    val youtubeLink: String?,
    val ingredients: List<Ingredient>
)
