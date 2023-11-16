package com.kotlincocktail.pourpal.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kotlincocktail.pourpal.entity.Recipe
@Dao
interface RecipeDao {
    @Query("SELECT * FROM Recipe")
    fun getAll(): List<Recipe>
    @Insert
    fun insertAll(vararg cocktail: Recipe)
    @Delete
    fun delete(cocktail: Recipe)
    // 他のデータベース操作
}