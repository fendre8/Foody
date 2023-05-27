package com.example.foody.model

import Meal
import MealMinimal
import getIngredient
import getMeasure

class Converter {
    fun convertMealsToRecipes(meals: List<Meal>): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        for (meal in meals) {
            recipes.add(convertMealToRecipe(meal))
        }
        return  recipes
    }

    private fun convertMealToRecipe(meal: Meal): Recipe {
        val ingredients = mutableListOf<Ingredient>()

        for (i in 1..20) {
            val ingredient = meal.getIngredient(i)
            val measure = meal.getMeasure(i)

            if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
                val ingredientObject = Ingredient(i, ingredient, measure)
                ingredients.add(ingredientObject)
            }
        }

        return Recipe(
            id = null,
            apiId = meal.idMeal.toInt(),
            mealName = meal.strMeal,
            category = meal.strCategory,
            area = meal.strArea,
            instructions = meal.strInstructions,
            mealThumb = meal.strMealThumb,
            tags = meal.strTags,
            youtubeLink = meal.strYoutube,
            ingredients = ingredients
        )
    }

    fun convertMinimalMealsToRecipes(meals: List<MealMinimal>): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        for (meal in meals) {
            recipes.add(convertMinimalMealToRecipe(meal))
        }
        return  recipes
    }

    private fun convertMinimalMealToRecipe(meal: MealMinimal): Recipe {
        return Recipe(
            id = null,
            apiId = meal.idMeal.toInt(),
            mealName = meal.strMeal,
            mealThumb = meal.strMealThumb,
            area = "",
            category = "",
            instructions = "",
            ingredients = emptyList(),
            youtubeLink = "",
            tags = ""
        )
    }
}