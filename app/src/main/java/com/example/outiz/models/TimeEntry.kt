package com.example.outiz.models

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
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
    ],
    indices = [
        Index(value = ["reportId"]),
        Index(value = ["technicianId"])
    ]
)
data class TimeEntry(
    @PrimaryKey val id: String,
    val reportId: String,
    val technicianId: String,
    val date: Date,
    val duration: Long // Durée en minutes
) : Parcelable