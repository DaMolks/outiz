package com.example.outiz.data.dao

import androidx.room.*
import com.example.outiz.models.Report
import com.example.outiz.models.ReportWithDetails
import com.example.outiz.models.TimeEntry
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ReportDao {
    @Transaction
    @Query("SELECT * FROM reports ORDER BY callDate DESC")
    fun getAllReports(): Flow<List<ReportWithDetails>>

    @Transaction
    @Query("SELECT * FROM reports WHERE id = :id")
    suspend fun getReportById(id: String): ReportWithDetails?

    @Transaction
    @Query("SELECT * FROM reports WHERE siteId = :siteId ORDER BY callDate DESC")
    fun getReportsBySite(siteId: String): Flow<List<ReportWithDetails>>

    @Transaction
    @Query("SELECT * FROM reports WHERE callDate BETWEEN :startDate AND :endDate ORDER BY callDate DESC")
    fun getReportsByDateRange(startDate: Date, endDate: Date): Flow<List<ReportWithDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: Report): Long

    @Update
    suspend fun updateReport(report: Report)

    @Delete
    suspend fun deleteReport(report: Report)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeEntry(timeEntry: TimeEntry)

    @Query("SELECT * FROM time_entries WHERE reportId = :reportId")
    fun getTimeEntriesForReport(reportId: String): Flow<List<TimeEntry>>

    @Query("DELETE FROM time_entries WHERE reportId = :reportId")
    suspend fun deleteTimeEntriesForReport(reportId: String)

    @Transaction
    suspend fun updateReportWithTimeEntries(report: Report, timeEntries: List<TimeEntry>) {
        updateReport(report)
        deleteTimeEntriesForReport(report.id)
        timeEntries.forEach { insertTimeEntry(it) }
    }
}