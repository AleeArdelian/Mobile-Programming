package com.example.examen

import androidx.room.*

@Dao
interface ObjectDAO {
    @Query("SELECT * FROM object")
    fun getAll(): List<Object>

    @Insert //(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg obj: Object) //: Long

    @Delete
    fun delete(obj: Object)

    @Query("DELETE FROM object")
    fun deleteAll()

    @Update
    fun updateDoctor(vararg obj: Object)
}