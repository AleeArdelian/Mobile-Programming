package com.example.vet_house

import androidx.room.*

@Dao
interface UserDAO {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

//    @Query("SELECT * FROM user WHERE email LIKE :email")
//    fun findByEmail(email: String): User

    @Insert
    fun insert(vararg user: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user")
    fun deleteAll()

    @Update
    fun updateDoctor(vararg user: User)
}