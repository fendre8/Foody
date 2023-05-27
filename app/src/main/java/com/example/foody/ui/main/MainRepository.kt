package com.example.foody.ui.main

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.foody.model.Converter
import com.example.foody.model.Recipe
import com.example.foody.network.FoodyService
import com.example.foody.persistence.FoodyDao
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val foodyService: FoodyService,
    private val foodyDao: FoodyDao
) {
    private val converter = Converter()

    @WorkerThread
    fun loadMeals(
        query: String = "",
        onStart: () -> Unit,
        onCompletion: () -> Unit,
    ) = flow {
        foodyService.fetchMealList(query)
            .suspendOnSuccess {
                if (data.meals != null) {
                    val meals = converter.convertMealsToRecipes(data.meals!!)
                    emit(meals)
                }
                else {
                    emit(emptyList<Recipe>())
                }
            }
            .suspendOnFailure {
                Log.d("FAILURE", "fail")
            }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)

    @WorkerThread
    suspend fun insertFavouriteMeal(recipe: Recipe) {
        withContext(Dispatchers.IO) {
            foodyDao.insertRecipe(recipe)
        }
    }

    @WorkerThread
    fun loadCategories() = flow {
        foodyService.listAllCategories()
            .suspendOnSuccess {
                val categories = data.categories
                emit(categories)
            }
    }.flowOn(Dispatchers.IO)
    @WorkerThread
    fun loadFavouriteRecipes() = flow {
        val recipes = foodyDao.getRecipes()
        emit(recipes)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadMealsByCategory(categoryName: String) = flow {
        foodyService.filterCategories(categoryName)
            .suspendOnSuccess {
                val recipes = converter.convertMinimalMealsToRecipes(data.meals)
                recipes.onEach { it.category = categoryName }
                emit(recipes)
            }
    }.flowOn(Dispatchers.IO)
}