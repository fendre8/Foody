package com.example.foody.mock

import CategoryArray
import MealArray
import MealMinimalArray
import com.example.foody.network.FoodyService
import retrofit2.Call

class MockFoodyService : FoodyService {
    override fun fetchMealList(s: String): Call<MealArray> {
        TODO("Not yet implemented")
    }

    override fun lookUpMeal(i: String): Call<MealArray> {
        TODO("Not yet implemented")
    }

    override fun listAllCategories(): Call<CategoryArray> {
        TODO("Not yet implemented")
    }

    override fun filterCategories(c: String): Call<MealMinimalArray> {
        TODO("Not yet implemented")
    }
}