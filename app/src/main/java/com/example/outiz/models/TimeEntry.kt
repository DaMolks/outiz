package com.example.outiz.models

import androidx.room.*
import java.time.LocalDateTime

@Entity(
    tableName = "time_entries",
    indices = [Index("reportId")],
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
    val startTime: LocalDateTime,
    val duration: Int, // in minutes
    val description: String
)