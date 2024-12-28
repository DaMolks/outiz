package com.example.outiz.models

import androidx.room.Embedded
import androidx.room.Relation

data class ReportWithDetails(
    @Embedded val report: Report,
    @Relation(
        parentColumn = "id",
        entityColumn = "reportId"
    )
    val timeEntries: List<TimeEntry> = listOf()
)