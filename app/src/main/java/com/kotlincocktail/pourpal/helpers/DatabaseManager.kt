package com.kotlincocktail.pourpal.helpers

import android.content.Context
import androidx.room.Room
import com.kotlincocktail.pourpal.database.Schema

object DatabaseManager {
    lateinit var database: Schema
        private set

    fun initialize(context: Context) {
        database = Room.databaseBuilder(context, Schema::class.java, "my-database").fallbackToDestructiveMigration().createFromAsset("pourpal.db").build()
    }

}