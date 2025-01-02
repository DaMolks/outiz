package com.example.outiz.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "technicians")
data class Technician(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val firstName: String,
    val lastName: String,
    val employeeId: String,
    val sector: String
)