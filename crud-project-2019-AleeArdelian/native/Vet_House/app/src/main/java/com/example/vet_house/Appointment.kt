package com.example.vet_house

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Appointment (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "doctor") var doctor_name: String = "Default Doctor name",
    @ColumnInfo(name = "check") var medical_check: String = "Default medical check",
    @ColumnInfo(name = "date") var date: String = "Default date"

)