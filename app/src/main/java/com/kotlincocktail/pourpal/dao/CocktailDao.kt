package com.kotlincocktail.pourpal.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kotlincocktail.pourpal.entity.Cocktail

@Dao
interface CocktailDao {
    @Query("SELECT * FROM Cocktail")
    fun getAll(): List<Cocktail>

    @Query("SELECT * FROM Cocktail WHERE cocktail_name IN (:names)")
    fun findCocktailsByName(names: List<String>): List<Cocktail>
    @Insert
    fun insertAll(vararg cocktail: Cocktail)
    @Delete
    fun delete(cocktail: Cocktail)
        // 他のデータベース操作
}