package com.example.outiz.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "reports")
@TypeConverters(DateConverter::class)
data class Report(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val siteName: String,
    val description: String,
    val date: Date,
    val caller: String,
    val callDate: Date,
    val callReason: String,
    val hasTimeTracking: Boolean,
    val hasPhotos: Boolean
)