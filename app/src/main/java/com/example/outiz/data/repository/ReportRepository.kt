package com.example.outiz.data.repository

import com.example.outiz.data.dao.ReportDao
import com.example.outiz.models.Report
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ReportRepository @Inject constructor(
    private val reportDao: ReportDao
) {
    suspend fun insert(report: Report): Long = reportDao.insert(report)

    suspend fun update(report: Report) = reportDao.insert(report)

    suspend fun delete(report: Report) = reportDao.delete(report)

    fun getAllReports(): Flow<List<Report>> = reportDao.getAllReports()

    suspend fun getReportById(reportId: Long): Report? {
        return reportDao.getReportById(reportId).first()
    }
}