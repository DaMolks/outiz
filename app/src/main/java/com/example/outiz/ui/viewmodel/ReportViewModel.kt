package com.example.outiz.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.outiz.data.OutizDatabase
import com.example.outiz.models.Report
import com.example.outiz.models.TimeEntry
import com.example.outiz.models.Technician
import kotlinx.coroutines.launch

class ReportViewModel(application: Application) : AndroidViewModel(application) {
    private val database = OutizDatabase.getDatabase(application)
    private val timeEntryDao = database.timeEntryDao()
    private val technicianDao = database.technicianDao()

    private val _timeEntries = MutableLiveData<List<TimeEntry>>()
    val timeEntries: LiveData<List<TimeEntry>> = _timeEntries

    fun loadTimeEntries(reportId: String) {
        viewModelScope.launch {
            _timeEntries.value = timeEntryDao.getEntriesForReport(reportId)
        }
    }

    fun addTimeEntry(timeEntry: TimeEntry) {
        viewModelScope.launch {
            timeEntryDao.insert(timeEntry)
            loadTimeEntries(timeEntry.reportId)
        }
    }

    fun updateTimeEntry(timeEntry: TimeEntry) {
        viewModelScope.launch {
            timeEntryDao.update(timeEntry)
            loadTimeEntries(timeEntry.reportId)
        }
    }

    fun deleteTimeEntry(timeEntry: TimeEntry) {
        viewModelScope.launch {
            timeEntryDao.delete(timeEntry)
            loadTimeEntries(timeEntry.reportId)
        }
    }

    fun getCurrentTechnicianId(): String {
        val prefs = PreferenceManager.getDefaultSharedPreferences(getApplication())
        return prefs.getString("technician_id", "1") ?: "1"
    }

    fun getTechnicianInfo(): LiveData<Technician> = technicianDao.getCurrentTechnician()
}