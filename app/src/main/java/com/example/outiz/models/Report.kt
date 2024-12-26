package com.example.outiz.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "reports",
    foreignKeys = [
        ForeignKey(
            entity = Site::class,
            parentColumns = ["id"],
            childColumns = ["siteId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Report(
    @PrimaryKey val id: String,
    val callDate: Date,
    val callReason: String,
    val caller: String,
    val siteId: String,
    val actions: List<String>,
    val photos: List<String>?,
    val hasSignature: Boolean = false,
    val signatureDate: Date? = null,
    val isTimeTrackingEnabled: Boolean = true,
    val isPhotosEnabled: Boolean = true
)

@Entity(
    tableName = "time_entries",
    foreignKeys = [
        ForeignKey(
            entity = Report::class,
            parentColumns = ["id"],
            childColumns = ["reportId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Technician::class,
            parentColumns = ["id"],
            childColumns = ["technicianId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TimeEntry(
    @PrimaryKey val id: String,
    val reportId: String,
    val technicianId: String,
    val date: Date,
    val duration: Long // Durée en minutes
)
