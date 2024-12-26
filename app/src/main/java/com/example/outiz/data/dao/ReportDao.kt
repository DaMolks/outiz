package com.example.outiz.data.dao

import androidx.room.*
import com.example.outiz.models.Report
import com.example.outiz.models.TimeEntry
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ReportDao {
    @Query("SELECT * FROM reports ORDER BY callDate DESC")
    fun getAllReports(): Flow<List<Report>>

    @Query("SELECT * FROM reports WHERE id = :id")
    suspend fun getReportById(id: String): Report?

    @Query("SELECT * FROM reports WHERE siteId = :siteId ORDER BY callDate DESC")
    fun getReportsBySite(siteId: String): Flow<List<Report>>

    @Query("SELECT * FROM reports WHERE callDate BETWEEN :startDate AND :endDate")
    fun getReportsByDateRange(startDate: Date, endDate: Date): Flow<List<Report>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: Report)

    @Update
    suspend fun updateReport(report: Report)

    @Delete
    suspend fun deleteReport(report: Report)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeEntry(timeEntry: TimeEntry)

    @Query("SELECT * FROM time_entries WHERE reportId = :reportId")
    fun getTimeEntriesForReport(reportId: String): Flow<List<TimeEntry>>
}
