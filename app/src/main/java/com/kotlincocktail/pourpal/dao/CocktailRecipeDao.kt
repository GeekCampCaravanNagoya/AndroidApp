package com.kotlincocktail.pourpal.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kotlincocktail.pourpal.entity.CocktailRecipe

@Dao
interface CocktailRecipeDao {
    @Query("SELECT * FROM CocktailRecipe")
    fun getAll(): List<CocktailRecipe>
    @Insert
    fun insertAll(vararg cocktail: CocktailRecipe)
    @Delete
    fun delete(cocktail: CocktailRecipe)
    // 他のデータベース操作
}