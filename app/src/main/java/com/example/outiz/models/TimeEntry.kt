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
    @PrimaryKey val id: String,
    val reportId: String,
    val technicianFirstName: String,
    val technicianLastName: String,
    val arrivalTime: Date,
    val departureTime: Date,
    val interventionDuration: Int,
    val travelDuration: Int
)