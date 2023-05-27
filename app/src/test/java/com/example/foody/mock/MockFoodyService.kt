package com.example.foody.mock

import CategoryArray
import MealArray
import MealMinimalArray
import com.example.foody.network.FoodyService
import com.skydoves.sandwich.ApiResponse
import retrofit2.Call

class MockFoodyService : FoodyService {
    override fun fetchMealList(s: String?): ApiResponse<MealArray> {
        TODO("Not yet implemented")
    }

    override fun lookUpMeal(i: String): ApiResponse<MealArray> {
        TODO("Not yet implemented")
    }

    override fun listAllCategories(): ApiResponse<CategoryArray> {
        TODO("Not yet implemented")
    }

    override fun filterCategories(c: String): ApiResponse<MealMinimalArray> {
        TODO("Not yet implemented")
    }
}