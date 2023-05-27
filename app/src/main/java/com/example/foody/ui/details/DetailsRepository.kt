package com.example.foody.ui.details

import androidx.annotation.WorkerThread
import com.example.foody.model.Converter
import com.example.foody.model.Recipe
import com.example.foody.network.FoodyService
import com.example.foody.persistence.FoodyDao
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val foodyDao: FoodyDao,
    private val foodyService: FoodyService
) {
    private val converter = Converter()

    @WorkerThread
    fun getMealById(id: Int) = flow {
        val recipe = foodyDao.getRecipe(id)
        if (recipe != null) {
            emit(recipe)
        } else {
            foodyService.lookUpMeal(id.toString())
                .suspendOnSuccess {
                    if (data.meals != null) {
                        val meal = converter.convertMealsToRecipes(data.meals!!).first()
                        emit(meal)
                    } else {
                        emit(null)
                    }
                }
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    suspend fun saveRecipe(recipe: Recipe) {
        withContext(Dispatchers.IO) {
            val dbRecipe = foodyDao.getRecipe(recipe.apiId)
            if (dbRecipe == null) {
                foodyDao.insertRecipe(recipe)
            } else {
                foodyDao.updateRecipe(recipe)
            }
        }
    }
}