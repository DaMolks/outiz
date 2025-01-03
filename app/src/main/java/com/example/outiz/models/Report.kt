package com.example.outiz.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "reports")
data class Report(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val siteName: String = "",
    val description: String = "",
    val date: Date = Date(),
    val hasTimeTracking: Boolean = false,
    val hasPhotos: Boolean = false,
    val photoPaths: List<String> = emptyList(),
    val caller: String = "",
    val callDate: Date = Date(),
    val callReason: String = ""
)