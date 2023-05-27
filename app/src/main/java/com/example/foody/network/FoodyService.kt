package com.example.foody.network

import CategoryArray
import MealArray
import MealMinimalArray
import com.skydoves.sandwich.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodyService {

    @GET("search.php")
    suspend fun fetchMealList(@Query("s") s: String?): ApiResponse<MealArray>

    @GET("lookup.php")
    suspend fun lookUpMeal(@Query("i") i: String): ApiResponse<MealArray>

    @GET("categories.php")
    suspend fun listAllCategories(): ApiResponse<CategoryArray>

    @GET("filter.php")
    suspend fun filterCategories(@Query("c") c: String): ApiResponse<MealMinimalArray>
}