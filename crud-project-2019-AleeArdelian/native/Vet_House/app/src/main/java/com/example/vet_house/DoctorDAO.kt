package com.example.vet_house

import androidx.room.*
@Dao
interface DoctorDAO {
    @Query("SELECT * FROM doctor")
    fun getAll(): List<Doctor>

  //  @Query("SELECT * FROM doctor WHERE email LIKE :email")
//    fun findByEmail(name: String): Doctor

    @Insert //(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg doctor: Doctor) //: Long

    @Delete
    fun delete(doctor: Doctor)

    @Query("DELETE FROM doctor")
    fun deleteAll()

    @Update
    fun updateDoctor(vararg doctor: Doctor)
}
