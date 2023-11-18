package com.kotlincocktail.pourpal.database

import com.kotlincocktail.pourpal.entity.Recipe
import androidx.room.Database
import androidx.room.RoomDatabase
import com.kotlincocktail.pourpal.dao.CocktailDao
import com.kotlincocktail.pourpal.dao.CocktailRecipeDao
import com.kotlincocktail.pourpal.dao.RecipeDao
import com.kotlincocktail.pourpal.entity.Cocktail
import com.kotlincocktail.pourpal.entity.CocktailRecipe

@Database(entities = [Cocktail::class, Recipe::class, CocktailRecipe::class], version = 3)

abstract class Schema : RoomDatabase() {
    abstract fun CocktailDao(): CocktailDao
    abstract fun RecipeDao(): RecipeDao
    abstract fun CocktailRecipeDao(): CocktailRecipeDao
}