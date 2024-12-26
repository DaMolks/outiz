package com.example.outiz.ui.reports

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.OutizDatabase
import com.example.outiz.models.ReportWithDetails
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.Calendar

class ReportsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = OutizDatabase.getDatabase(application)
    private val reportDao = database.reportDao()

    private val _reports = MutableLiveData<List<ReportWithDetails>>()
    val reports: LiveData<List<ReportWithDetails>> = _reports

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadReports()
    }

    fun loadReports() {
        viewModelScope.launch {
            reportDao.getAllReports()
                .catch { e -> _error.value = e.message }
                .collect { reports -> _reports.value = reports }
        }
    }

    fun loadReportsForWeek() {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val endDate = calendar.time

            calendar.add(Calendar.DAY_OF_WEEK, -7)
            val startDate = calendar.time

            reportDao.getReportsByDateRange(startDate, endDate)
                .catch { e -> _error.value = e.message }
                .collect { reports -> _reports.value = reports }
        }
    }

    fun loadReportsForMonth() {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val endDate = calendar.time

            calendar.add(Calendar.MONTH, -1)
            val startDate = calendar.time

            reportDao.getReportsByDateRange(startDate, endDate)
                .catch { e -> _error.value = e.message }
                .collect { reports -> _reports.value = reports }
        }
    }

    fun deleteReport(report: ReportWithDetails) {
        viewModelScope.launch {
            try {
                reportDao.deleteReport(report.report)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}