package com.kotlincocktail.pourpal.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cocktail (
    @PrimaryKey val cocktail_id : Int,
    val cocktail_name: String,
    val cocktail_name_english: String,
    val base_name: String,
    val technique_name: String,
    val taste_name: String,
    val style_name: String,
    val alcohol: Int,
    val top_name: String,
    val glass_name: String,
    val type_name: String,
    val cocktail_digest: String,
    val cocktail_desc: String,
    val recipe_desc: String,
    val cocktail_img: String
)