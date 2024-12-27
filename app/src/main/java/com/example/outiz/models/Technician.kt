package com.example.outiz.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "technicians")
data class Technician(
    @PrimaryKey
    val id: String,
    val firstName: String,
    val lastName: String,
    val sector: String,
    val identifier: String = "",
    val supervisor: String = ""
)