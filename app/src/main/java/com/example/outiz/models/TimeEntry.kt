package com.example.outiz.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "time_entries")
data class TimeEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "report_id") val reportId: Long,
    val description: String,
    @ColumnInfo(name = "start_time") val startTime: LocalDateTime,
    val duration: Int
)