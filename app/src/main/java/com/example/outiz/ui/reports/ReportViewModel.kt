package com.example.outiz.ui.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.TimeEntryDao
import com.example.outiz.models.TimeEntry
import com.example.outiz.utils.AppPreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ReportViewModel(
    private val reportDao: ReportDao,
    private val timeEntryDao: TimeEntryDao,
    private val preferenceManager: AppPreferenceManager
) : ViewModel() {

    fun getTimeEntriesForReport(reportId: Long): Flow<List<TimeEntry>> = 
        timeEntryDao.getTimeEntriesForReport(reportId)

    fun insertTimeEntry(timeEntry: TimeEntry) = viewModelScope.launch {
        timeEntryDao.insert(timeEntry)
    }

    fun deleteTimeEntry(timeEntry: TimeEntry) = viewModelScope.launch {
        timeEntryDao.delete(timeEntry)
    }

    class Factory(
        private val reportDao: ReportDao,
        private val timeEntryDao: TimeEntryDao,
        private val preferenceManager: AppPreferenceManager
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ReportViewModel(reportDao, timeEntryDao, preferenceManager) as T
        }
    }
}