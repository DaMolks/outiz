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
import com.example.outiz.utils.AppPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportDao: ReportDao,
    private val timeEntryDao: TimeEntryDao,
    private val preferenceManager: AppPreferenceManager
) : ViewModel() {

    private val _currentReport = MutableLiveData<Report?>()
    val currentReport: LiveData<Report?> = _currentReport

    val timeEntries: LiveData<List<TimeEntry>> = timeEntryDao.getAllTimeEntries().asLiveData()

    // Report fields
    private val _siteName = MutableLiveData<String>()
    val siteName: LiveData<String> = _siteName

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val _date = MutableLiveData(LocalDateTime.now())
    val date: LiveData<LocalDateTime> = _date

    fun loadReport(reportId: Long) {
        viewModelScope.launch {
            reportDao.getReportById(reportId).collect { report ->
                _currentReport.value = report
                report?.let {
                    _siteName.value = it.siteName
                    _description.value = it.description
                    _date.value = it.date
                }
            }
        }
    }

    fun saveReport(onComplete: (Boolean) -> Unit) {
        val report = createReportFromCurrentState()
        viewModelScope.launch {
            try {
                reportDao.insert(report)
                onComplete(true)
            } catch (e: Exception) {
                onComplete(false)
            }
        }
    }

    private fun createReportFromCurrentState(): Report {
        return Report(
            id = currentReport.value?.id ?: 0,
            siteName = siteName.value.orEmpty(),
            description = description.value.orEmpty(),
            date = date.value ?: LocalDateTime.now(),
            technicians = emptyList(),
            photosPaths = emptyList()
        )
    }

    fun updateSiteName(name: String) {
        _siteName.value = name
    }

    fun updateDescription(desc: String) {
        _description.value = desc
    }

    fun updateDate(newDate: LocalDateTime) {
        _date.value = newDate
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
}