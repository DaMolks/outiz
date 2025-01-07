package com.example.outiz.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.outiz.models.ReportWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDao {
    @Query("SELECT * FROM reports WHERE created_at BETWEEN :start AND :end")
    fun getReportsByDateRange(start: Long, end: Long): Flow<List<ReportWithDetails>>
}