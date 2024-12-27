package com.example.outiz.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.outiz.data.OutizDatabase
import com.example.outiz.models.Report
import com.example.outiz.models.ReportWithDetails
import com.example.outiz.models.TimeEntry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ReportViewModel(application: Application) : AndroidViewModel(application) {
    private val database = OutizDatabase.getDatabase(application)
    private val reportDao = database.reportDao()
    private val technicianDao = database.technicianDao()
    private val prefs = PreferenceManager.getDefaultSharedPreferences(application)

    private val _reports = MutableLiveData<List<Report>>()
    val reports: LiveData<List<Report>> = _reports

    private val _timeEntries = MutableLiveData<List<TimeEntry>>()
    val timeEntries: LiveData<List<TimeEntry>> = _timeEntries

    fun loadReports() {
        viewModelScope.launch {
            val reportsWithDetails = reportDao.getAllReports().first()
            _reports.value = reportsWithDetails.map { it.report }
        }
    }

    fun loadTimeEntries(reportId: String) {
        viewModelScope.launch {
            _timeEntries.value = reportDao.getTimeEntriesForReport(reportId).first()
        }
    }

    fun addTimeEntry(timeEntry: TimeEntry) {
        viewModelScope.launch {
            reportDao.insertTimeEntry(timeEntry)
            loadTimeEntries(timeEntry.reportId)
        }
    }

    fun updateTimeEntry(timeEntry: TimeEntry) {
        viewModelScope.launch {
            reportDao.insertTimeEntry(timeEntry)
            loadTimeEntries(timeEntry.reportId)
        }
    }

    fun deleteTimeEntry(timeEntry: TimeEntry) {
        viewModelScope.launch {
            reportDao.deleteTimeEntriesForReport(timeEntry.reportId)
            loadTimeEntries(timeEntry.reportId)
        }
    }

    fun getCurrentTechnicianInfo() = technicianDao.getCurrentTechnician().asLiveData()

    fun deleteReport(report: Report) {
        viewModelScope.launch {
            reportDao.deleteReport(report)
            loadReports()
        }
    }

    fun getCurrentTechnicianId(): String {
        return prefs.getString("technician_id", "1") ?: "1"
    }
}