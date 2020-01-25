package com.example.examen

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Object (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int ?= null,
    @ColumnInfo(name = "details") var details: String,
    @ColumnInfo(name = "status") var status: String,
    @ColumnInfo(name = "user") var user: Int,
    @ColumnInfo(name = "age") var age: Int,
    @ColumnInfo(name = "type") var type: String
)