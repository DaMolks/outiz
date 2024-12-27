package com.example.outiz.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "time_entries",
    foreignKeys = [
        ForeignKey(
            entity = Report::class,
            parentColumns = ["id"],
            childColumns = ["reportId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TimeEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val reportId: Long,
    val technicianFirstName: String,
    val technicianLastName: String,
    val isOwner: Boolean, // Pour identifier si c'est le propriétaire du téléphone
    val arrivalTime: Date,
    val departureTime: Date,
    val interventionDuration: Int, // En minutes
    val travelDuration: Int // En minutes
)