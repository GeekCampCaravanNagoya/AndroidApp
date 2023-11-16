package com.kotlincocktail.pourpal.dataclasses

data class RecipeDataClass(
    val ingredient_id: Number,
    val ingredient_name: String,
    val amount: Number,
    val unit: String
)
