package com.example.outiz.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.dao.TimeEntryDao
import com.example.outiz.models.TimeEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimeTrackingViewModel @Inject constructor(
    private val timeEntryDao: TimeEntryDao
) : ViewModel() {

    private val _timeEntries = MutableLiveData<List<TimeEntry>>()
    val timeEntries: LiveData<List<TimeEntry>> = _timeEntries

    init {
        loadTimeEntries()
    }

    fun loadTimeEntries() {
        viewModelScope.launch {
            _timeEntries.value = timeEntryDao.getAllTimeEntries()
        }
    }

    fun addTimeEntry(timeEntry: TimeEntry) {
        viewModelScope.launch {
            timeEntryDao.insert(timeEntry)
            loadTimeEntries()
        }
    }

    fun deleteTimeEntry(timeEntry: TimeEntry) {
        viewModelScope.launch {
            timeEntryDao.delete(timeEntry)
            loadTimeEntries()
        }
    }
}