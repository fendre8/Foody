package com.example.foody.network

import CategoryArray
import MealArray
import MealMinimalArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodyService {

    @GET("search.php")
    fun fetchMealList(@Query("s") s: String): Call<MealArray>

    @GET("lookup.php")
    fun lookUpMeal(@Query("i") i: String): Call<MealArray>

    @GET("categories.php")
    fun listAllCategories(): Call<CategoryArray>

    @GET("filter.php")
    fun filterCategories(@Query("c") c: String): Call<MealMinimalArray>
}