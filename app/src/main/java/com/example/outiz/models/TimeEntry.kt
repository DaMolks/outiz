package com.example.outiz.models

import androidx.room.*
import java.util.*

@Entity(
    tableName = "time_entries",
    indices = [
        Index(value = ["report_id", "start_time"]),
        Index(value = ["report_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = Report::class,
            parentColumns = ["id"],
            childColumns = ["report_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TimeEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "report_id")
    val reportId: Long,
    val description: String,
    @ColumnInfo(name = "start_time")
    val startTime: Date,
    val duration: Int // in minutes
)