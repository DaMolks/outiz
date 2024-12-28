package com.example.outiz.ui.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.models.ReportWithDetails
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class ReportsViewModel(
    private val reportDao: ReportDao
) : ViewModel() {

    val reportsWithDetails = reportDao.getReportsWithDetails().asLiveData()

    fun getReportsByDateRange(start: LocalDateTime, end: LocalDateTime) = 
        reportDao.getReportsByDateRange(start, end)

    fun deleteReport(report: ReportWithDetails) = viewModelScope.launch {
        reportDao.delete(report.report)
    }

    class Factory(private val reportDao: ReportDao) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ReportsViewModel(reportDao) as T
        }
    }
}