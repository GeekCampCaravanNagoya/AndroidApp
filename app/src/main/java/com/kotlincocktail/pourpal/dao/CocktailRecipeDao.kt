package com.kotlincocktail.pourpal.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kotlincocktail.pourpal.entity.CocktailRecipe
import com.kotlincocktail.pourpal.entity.Recipe

@Dao
interface CocktailRecipeDao {
    @Query("SELECT * FROM CocktailRecipe")
    fun getAll(): List<CocktailRecipe>
    @Insert
    fun insertAll(vararg cocktail: CocktailRecipe)
    @Delete
    fun delete(cocktail: CocktailRecipe)
    // 他のデータベース操作
    // IDの配列に基づいてカクテルレシピを取得するクエリ
    @Query("""
        SELECT * FROM CocktailRecipe
        INNER JOIN Recipe ON CocktailRecipe.ingredient_id = Recipe.ingredient_id
        WHERE CocktailRecipe.cocktail_id IN (:cocktailIds)
    """)
    fun getCocktailRecipesWithJoin(cocktailIds: IntArray): List<Recipe>


}