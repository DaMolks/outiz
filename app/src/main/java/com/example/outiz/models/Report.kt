package com.example.outiz.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDateTime

@Entity(tableName = "reports")
data class Report(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val siteId: Long,
    val date: LocalDateTime,
    val description: String,
    val technicians: List<String> = listOf(),
    val status: String
)

data class ReportWithDetails(
    @Embedded val report: Report,
    @Relation(
        parentColumn = "id",
        entityColumn = "reportId"
    )
    val timeEntries: List<TimeEntry>
)