package com.example.vet_house

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Doctor::class, Appointment::class, User::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun doctor(): DoctorDAO
    abstract fun appointment() :AppointmentDAO
    abstract fun user() : UserDAO

}