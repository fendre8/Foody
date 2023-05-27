package com.example.foody.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.foody.model.Recipe

@Dao
interface FoodyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(meals: List<Recipe>)

    @Query("SELECT * FROM Recipe")
    suspend fun getRecipes(): List<Recipe>

    @Query("SELECT * FROM Recipe WHERE apiId = :id_")
    suspend fun getRecipe(id_: Int): Recipe?

    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("DELETE FROM Recipe")
    suspend fun deleteAllRecipes()
}