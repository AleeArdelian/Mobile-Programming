package com.example.vet_house

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") var id: Int ?= null,
        @ColumnInfo(name = "first_name") var firstName: String?,
        @ColumnInfo(name = "last_name") var lastName: String?,
        @ColumnInfo(name = "email") var email: String?,
        @ColumnInfo(name = "password") var password: String?
)