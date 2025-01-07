package com.example.outiz.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.dao.TimeEntryDao
import com.example.outiz.models.TimeEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimeEntryViewModel @Inject constructor(
    private val timeEntryDao: TimeEntryDao
) : ViewModel() {

    fun getTimeEntriesForReport(reportId: Long): LiveData<List<TimeEntry>> =
        timeEntryDao.getTimeEntriesForReport(reportId)

    fun insertTimeEntry(timeEntry: TimeEntry) = viewModelScope.launch {
        timeEntryDao.insertTimeEntry(timeEntry)
    }

    fun updateTimeEntry(timeEntry: TimeEntry) = viewModelScope.launch {
        timeEntryDao.updateTimeEntry(timeEntry)
    }

    fun deleteTimeEntry(timeEntry: TimeEntry) = viewModelScope.launch {
        timeEntryDao.deleteTimeEntry(timeEntry)
    }

    fun getTotalDurationForReport(reportId: Long): LiveData<Int> =
        timeEntryDao.getTotalDurationForReport(reportId)
}