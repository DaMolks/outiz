package com.example.outiz.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.repository.ReportRepository
import com.example.outiz.models.Report
import com.example.outiz.models.TimeEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repository: ReportRepository
) : ViewModel() {

    private val _currentReport = MutableLiveData<Report?>()
    val currentReport: LiveData<Report?> = _currentReport

    private val _timeEntries = MutableLiveData<List<TimeEntry>>()
    val timeEntries: LiveData<List<TimeEntry>> = _timeEntries

    private val _photoPaths = MutableLiveData<List<String>>(emptyList())
    val photoPaths: LiveData<List<String>> = _photoPaths

    fun loadReport(reportId: Long) {
        viewModelScope.launch {
            repository.getReportById(reportId).collect { report ->
                _currentReport.value = report
            }
            repository.getTimeEntriesForReport(reportId).collect { entries ->
                _timeEntries.value = entries
            }
        }
    }

    fun insertTimeEntry(timeEntry: TimeEntry) {
        viewModelScope.launch {
            repository.insertTimeEntry(timeEntry)
        }
    }

    fun deleteTimeEntry(timeEntry: TimeEntry) {
        viewModelScope.launch {
            repository.deleteTimeEntry(timeEntry)
        }
    }

    fun updateReport(report: Report) {
        viewModelScope.launch {
            repository.updateReport(report)
        }
    }

    fun removePhoto(photoPath: String) {
        val currentPaths = _photoPaths.value?.toMutableList() ?: mutableListOf()
        currentPaths.remove(photoPath)
        _photoPaths.value = currentPaths
        _currentReport.value?.let { report ->
            updateReport(report.copy(photoPaths = currentPaths))
        }
    }

    fun addPhoto(photoPath: String) {
        val currentPaths = _photoPaths.value?.toMutableList() ?: mutableListOf()
        currentPaths.add(photoPath)
        _photoPaths.value = currentPaths
        _currentReport.value?.let { report ->
            updateReport(report.copy(photoPaths = currentPaths))
        }
    }

    fun convertToLocalDateTime(date: Date): LocalDateTime {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
    }

    fun convertToDate(localDateTime: LocalDateTime): Date {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
    }
}