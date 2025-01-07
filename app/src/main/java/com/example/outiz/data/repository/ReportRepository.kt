package com.example.outiz.data.repository

import androidx.lifecycle.LiveData
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.models.Report
import javax.inject.Inject

class ReportRepository @Inject constructor(
    private val reportDao: ReportDao
) {
    val allReports: LiveData<List<Report>> = reportDao.getAllReports()

    suspend fun insert(report: Report) = reportDao.insertReport(report)

    suspend fun update(report: Report) = reportDao.updateReport(report)

    suspend fun delete(report: Report) = reportDao.deleteReport(report)

    suspend fun getReportById(id: Long): Report? = reportDao.getReportById(id)

    fun getReportsForPeriod(startDate: Long, endDate: Long): LiveData<List<Report>> =
        reportDao.getReportsForPeriod(startDate, endDate)
}