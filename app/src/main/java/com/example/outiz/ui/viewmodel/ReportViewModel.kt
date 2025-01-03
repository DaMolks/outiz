package com.example.outiz.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.TimeEntryDao
import com.example.outiz.models.Report
import com.example.outiz.models.TimeEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportDao: ReportDao,
    private val timeEntryDao: TimeEntryDao
) : ViewModel() {

    private val _currentReport = MutableLiveData<Report?>()
    val currentReport: LiveData<Report?> = _currentReport

    private val _photosPaths = MutableLiveData<List<String>>(emptyList())
    val photosPaths: LiveData<List<String>> = _photosPaths

    fun loadReport(reportId: Long) {
        viewModelScope.launch {
            reportDao.getReportById(reportId).collect { report ->
                _currentReport.value = report
                // Charger les chemins des photos associées au rapport
                report?.photoPaths?.let { paths ->
                    _photosPaths.value = paths
                }
            }
        }
    }

    fun getTimeEntries(reportId: Long): LiveData<List<TimeEntry>> {
        return timeEntryDao.getTimeEntriesForReport(reportId).asLiveData()
    }

    fun addTimeEntry(timeEntry: TimeEntry) {
        viewModelScope.launch {
            timeEntryDao.insert(timeEntry)
        }
    }

    fun removeTimeEntry(timeEntry: TimeEntry) {
        viewModelScope.launch {
            timeEntryDao.delete(timeEntry)
        }
    }

    fun removePhoto(photoPath: String) {
        viewModelScope.launch {
            _currentReport.value?.let { report ->
                val updatedPaths = report.photoPaths.toMutableList().apply { remove(photoPath) }
                val updatedReport = report.copy(photoPaths = updatedPaths)
                reportDao.update(updatedReport)
                _photosPaths.value = updatedPaths
            }
        }
    }

    fun addPhoto(photoPath: String) {
        viewModelScope.launch {
            _currentReport.value?.let { report ->
                val updatedPaths = report.photoPaths.toMutableList().apply { add(photoPath) }
                val updatedReport = report.copy(photoPaths = updatedPaths)
                reportDao.update(updatedReport)
                _photosPaths.value = updatedPaths
            }
        }
    }
}