package com.kotlincocktail.pourpal.entity
import androidx.room.Entity

@Entity(primaryKeys = ["cocktail_id", "ingredient_id"])
data class CocktailRecipe(
    val cocktail_id: Int,
    val ingredient_id: Int
)