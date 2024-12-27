package com.example.outiz.ui.reports

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.OutizDatabase
import com.example.outiz.models.Report
import com.example.outiz.models.TimeEntry
import kotlinx.coroutines.launch

class ReportViewModel(application: Application) : AndroidViewModel(application) {
    private val reportDao = OutizDatabase.getDatabase(application).reportDao()
    private val timeEntryDao = OutizDatabase.getDatabase(application).timeEntryDao()

    val currentReport = MutableLiveData<Report?>(null)
    val timeEntries = MutableLiveData<List<TimeEntry>>(emptyList())

    var hasTimeTracking = true
    var hasPhotos = true

    fun addTimeEntry(timeEntry: TimeEntry) {
        viewModelScope.launch {
            timeEntryDao.insert(timeEntry)
            val updatedEntries = timeEntryDao.getTimeEntriesForReport(timeEntry.reportId)
            timeEntries.postValue(updatedEntries)
        }
    }

    fun removeTimeEntry(timeEntry: TimeEntry) {
        viewModelScope.launch {
            timeEntryDao.delete(timeEntry)
            val updatedEntries = timeEntryDao.getTimeEntriesForReport(timeEntry.reportId)
            timeEntries.postValue(updatedEntries)
        }
    }
}