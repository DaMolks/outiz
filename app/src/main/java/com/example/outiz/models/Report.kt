package com.example.outiz.models

import androidx.room.*
import java.util.*

@Entity(
    tableName = "reports",
    indices = [
        Index(value = ["created_at"]),
        Index(value = ["site_id"]),
        Index(value = ["technician_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = Site::class,
            parentColumns = ["id"],
            childColumns = ["site_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Technician::class,
            parentColumns = ["id"],
            childColumns = ["technician_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Report(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val siteName: String,
    @ColumnInfo(name = "site_id")
    val siteId: Long,
    @ColumnInfo(name = "technician_id")
    val technicianId: Long,
    val description: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Date,
    val status: String = "DRAFT",
    val caller: String? = null,
    @ColumnInfo(name = "call_date")
    val callDate: Date? = null,
    @ColumnInfo(name = "call_reason")
    val callReason: String? = null
)