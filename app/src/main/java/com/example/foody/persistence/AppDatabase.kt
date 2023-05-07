package com.example.foody.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foody.model.Recipe

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun foodyDao(): FoodyDao
}