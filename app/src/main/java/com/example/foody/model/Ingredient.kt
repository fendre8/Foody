package com.example.foody.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredient(
    @PrimaryKey val id: Number,
    val name: String,
    val measure: String,
)
