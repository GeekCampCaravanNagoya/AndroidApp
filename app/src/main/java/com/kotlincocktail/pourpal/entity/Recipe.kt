package com.kotlincocktail.pourpal.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe (
    @PrimaryKey val ingredient_id: Int,
    val ingredient_name: String,
    val amount: Int,
    val unit: String
)