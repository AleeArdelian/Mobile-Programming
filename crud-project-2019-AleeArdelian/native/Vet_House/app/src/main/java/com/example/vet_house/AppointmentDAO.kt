package com.example.vet_house

import androidx.room.*


@Dao
interface AppointmentDAO {
    @Query("SELECT * FROM appointment")
    fun getAll(): List<Appointment>

    @Query("SELECT * FROM appointment WHERE doctor LIKE :name")
    fun findByName(name: String): Appointment

    @Insert
    fun insert(vararg appointment: Appointment)

    @Delete
    fun delete(app: Appointment)

    @Query("DELETE FROM appointment")
    fun deleteAll()

    @Update
    fun updateAppointment(vararg appointment: Appointment)
}