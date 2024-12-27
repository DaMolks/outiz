package com.example.outiz.models

import androidx.room.*
import java.util.Date

@Entity(
    tableName = "reports",
    foreignKeys = [
        ForeignKey(
            entity = Site::class,
            parentColumns = ["id"],
            childColumns = ["siteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["siteId"])
    ]
)
data class Report(
    @PrimaryKey val id: String,
    val callDate: Date,
    val callReason: String,
    val caller: String,
    val siteId: String,
    val actions: List<String>,
    val photosPaths: List<String>?,
    val hasSignature: Boolean = false,
    val signaturePath: String? = null,
    val signatureDate: Date? = null,
    val isTimeTrackingEnabled: Boolean = true,
    val isPhotosEnabled: Boolean = true
)

data class ReportWithDetails(
    @Embedded val report: Report,
    @Relation(
        parentColumn = "siteId",
        entityColumn = "id"
    )
    val site: Site,
    @Relation(
        parentColumn = "id",
        entityColumn = "reportId"
    )
    val timeEntries: List<TimeEntry>
)