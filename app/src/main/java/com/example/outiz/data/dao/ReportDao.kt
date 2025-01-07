package com.example.outiz.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.outiz.models.ReportWithDetails
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface ReportDao {
    @Query("SELECT * FROM reports WHERE date BETWEEN :start AND :end")
    fun getReportsByDateRange(start: Long, end: Long): Flow<List<ReportWithDetails>>
}