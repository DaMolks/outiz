package com.example.outiz.data.repository

import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.TimeEntryDao
import com.example.outiz.models.Report
import com.example.outiz.models.ReportWithDetails
import com.example.outiz.models.TimeEntry
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class ReportRepository @Inject constructor(
    private val reportDao: ReportDao,
    private val timeEntryDao: TimeEntryDao
) {
    fun getAllReports(): Flow<List<Report>> = reportDao.getAllReports()

    suspend fun insertReport(report: Report): Long {
        return reportDao.insertReport(report)
    }

    suspend fun updateReport(report: Report) {
        reportDao.updateReport(report)
    }

    suspend fun deleteReport(report: Report) {
        reportDao.deleteReport(report)
    }

    fun getReportById(id: Long): Flow<Report?> {
        return reportDao.getReportById(id)
    }

    fun getReportWithDetails(id: Long): Flow<ReportWithDetails?> {
        return reportDao.getReportWithDetails(id)
    }

    fun getReportsForPeriod(start: Date, end: Date): Flow<List<ReportWithDetails>> {
        return reportDao.getReportsByDateRange(start.time, end.time)
    }

    suspend fun insertTimeEntry(timeEntry: TimeEntry): Long {
        return timeEntryDao.insertTimeEntry(timeEntry)
    }

    suspend fun deleteTimeEntry(timeEntry: TimeEntry) {
        timeEntryDao.deleteTimeEntry(timeEntry)
    }
    
    fun getTimeEntriesForReport(reportId: Long): Flow<List<TimeEntry>> {
        return timeEntryDao.getTimeEntriesForReport(reportId)
    }
}