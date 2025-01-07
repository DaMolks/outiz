package com.example.outiz.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity(tableName = "report_with_details")
data class ReportWithDetails(
    @Embedded val report: Report,
    @Relation(
        parentColumn = "id",
        entityColumn = "report_id"
    )
    val timeEntries: List<TimeEntry>
)