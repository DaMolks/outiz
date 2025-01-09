package com.example.outiz.data.dao

import androidx.room.*
import com.example.outiz.models.Report
import com.example.outiz.models.ReportWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDao {
    @Query("SELECT * FROM reports")
    fun getAllReports(): Flow<List<Report>>

    @Query("SELECT * FROM reports WHERE id = :reportId")
    fun getReportById(reportId: Long): Flow<Report?>

    @Query("SELECT * FROM reports WHERE id = :reportId")
    fun getReportWithDetails(reportId: Long): Flow<ReportWithDetails?>

    @Query("SELECT * FROM reports WHERE created_at BETWEEN :start AND :end")
    fun getReportsByDateRange(start: Long, end: Long): Flow<List<ReportWithDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: Report): Long

    @Update
    suspend fun updateReport(report: Report)

    @Delete
    suspend fun deleteReport(report: Report)
}