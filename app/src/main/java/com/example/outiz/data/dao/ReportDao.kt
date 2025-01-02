package com.example.outiz.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.outiz.models.Report
import com.example.outiz.models.ReportWithDetails
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface ReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(report: Report): Long

    @Query("SELECT * FROM reports")
    fun getAllReports(): Flow<List<Report>>

    @Transaction
    @Query("SELECT * FROM reports")
    fun getReportsWithDetails(): Flow<List<ReportWithDetails>>

    @Transaction
    @Query("SELECT * FROM reports WHERE date BETWEEN :start AND :end")
    fun getReportsByDateRange(start: LocalDateTime, end: LocalDateTime): Flow<List<ReportWithDetails>>

    @Delete
    suspend fun delete(report: Report)

    @Query("DELETE FROM reports")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM reports WHERE id = :reportId")
    fun getReportById(reportId: Long): Flow<Report?>
}