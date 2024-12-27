package com.example.outiz.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
@Entity(tableName = "time_entries")
data class TimeEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val reportId: Long,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val description: String,
    val taskType: String
) : Parcelable