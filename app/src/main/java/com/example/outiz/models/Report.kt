package com.example.outiz.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "reports")
data class Report(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val siteName: String,
    val description: String,
    val hasTimeTracking: Boolean = false,
    val hasPhotos: Boolean = false,
    
    val createdAt: Date,
    val updatedAt: Date,
    
    val photoPaths: List<String> = emptyList(),
    
    // Champs optionnels
    val callReason: String? = null,
    val callerName: String? = null,
    val callDate: Date? = null,
    val completedAt: Date? = null,
    val technicianId: String? = null
)