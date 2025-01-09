package com.example.outiz.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.repository.ReportRepository
import com.example.outiz.models.TimeEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TimeTrackingViewModel @Inject constructor(
    private val repository: ReportRepository
) : ViewModel() {

    private val _timeEntries = MutableLiveData<List<TimeEntry>>()
    val timeEntries: LiveData<List<TimeEntry>> = _timeEntries

    private var currentReportId: Long = 0

    fun loadTimeEntries(reportId: Long) {
        currentReportId = reportId
        viewModelScope.launch {
            repository.getTimeEntriesForReport(reportId).collectLatest { entries ->
                _timeEntries.value = entries
            }
        }
    }

    suspend fun addTimeEntry(duration: Int, description: String, startTime: LocalDateTime) {
        val timeEntry = TimeEntry(
            reportId = currentReportId,
            duration = duration,
            description = description,
            startTime = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant())
        )
        repository.insertTimeEntry(timeEntry)
    }

    suspend fun removeTimeEntry(timeEntry: TimeEntry) {
        repository.deleteTimeEntry(timeEntry)
    }

    fun getTotalDuration(): Int {
        return _timeEntries.value?.sumOf { it.duration } ?: 0
    }

    fun convertToDate(localDateTime: LocalDateTime): Date {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
    }

    fun convertToLocalDateTime(date: Date): LocalDateTime {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
    }
}