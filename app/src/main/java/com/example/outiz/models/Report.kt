package com.example.outiz.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "reports")
data class Report(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val siteName: String,
    val description: String,
    val date: LocalDateTime,
    val technicians: List<String> = emptyList(),
    val photosPaths: List<String> = emptyList(),
    val hasTimeTracking: Boolean = true,
    val hasPhotos: Boolean = true
)