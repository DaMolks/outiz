package com.example.outiz.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sites")
data class Site(
    @PrimaryKey val id: String,
    val name: String,
    val address: String,
    val codeS: String,
    val clientName: String
)
