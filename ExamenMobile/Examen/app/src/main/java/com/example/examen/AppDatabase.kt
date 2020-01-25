package com.example.examen

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Object::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun objects(): ObjectDAO
}