package com.example.outiz.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index
import java.time.LocalDateTime

@Entity(
    tableName = "time_entries",
    foreignKeys = [ForeignKey(
        entity = Report::class,
        parentColumns = ["id"],
        childColumns = ["reportId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["reportId"])]
)
data class TimeEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val reportId: Long,
    val date: LocalDateTime,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val duration: Long,
    val description: String,
    val taskType: String
)