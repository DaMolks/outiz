package com.example.outiz.models

import androidx.room.*
import java.util.Date

@TypeConverters(DateConverter::class)
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
    val startTime: Date,
    val duration: Int, // in minutes
    val description: String
)

object DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}