package com.example.outiz.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
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
        )
    ],
    indices = [Index("reportId")]
)
data class TimeEntry(
    @PrimaryKey val id: String,
    val reportId: String,
    val technicianId: String,
    val date: Date,
    val duration: Long,
    val technicianFirstName: String? = null,
    val technicianLastName: String? = null,
    val arrivalTime: Date? = null,
    val departureTime: Date? = null,
    val interventionDuration: Int? = null,
    val travelDuration: Int? = null
) : Parcelable