package com.example.outiz.data.repository

import androidx.lifecycle.LiveData
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.TimeEntryDao
import com.example.outiz.models.Report
import com.example.outiz.models.ReportWithDetails
import com.example.outiz.models.TimeEntry
import javax.inject.Inject

class ReportRepository @Inject constructor(
    private val reportDao: ReportDao,
    private val timeEntryDao: TimeEntryDao
) {
    val allReports: LiveData<List<Report>> = reportDao.getAllReports()

    suspend fun insert(report: Report): Long {
        return reportDao.insertReport(report)
    }

    suspend fun update(report: Report) {
        reportDao.updateReport(report)
    }

    suspend fun delete(report: Report) {
        reportDao.deleteReport(report)
    }

    suspend fun getReportById(id: Long): Report? {
        return reportDao.getReportById(id)
    }

    suspend fun getReportWithDetails(reportId: Long): ReportWithDetails? {
        return reportDao.getReportWithDetails(reportId)
    }

    fun getReportsForPeriod(startDate: Long, endDate: Long): LiveData<List<Report>> {
        return reportDao.getReportsForPeriod(startDate, endDate)
    }

    suspend fun addTimeEntry(timeEntry: TimeEntry) {
        timeEntryDao.insertTimeEntry(timeEntry)
    }

    suspend fun deleteTimeEntry(timeEntry: TimeEntry) {
        timeEntryDao.deleteTimeEntry(timeEntry)
    }

    fun getTimeEntriesForReport(reportId: Long): LiveData<List<TimeEntry>> {
        return timeEntryDao.getTimeEntriesForReport(reportId)
    }
}