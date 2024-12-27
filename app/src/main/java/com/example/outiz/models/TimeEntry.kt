package com.example.outiz.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.Date

@Parcelize
@Entity(
    tableName = "time_entries",
    indices = [
        Index(value = ["reportId"])
    ]
)
data class TimeEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val reportId: String,
    val date: Date,
    val duration: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val description: String,
    val taskType: String,
    val technicians: List<String> = listOf()
) : Parcelable