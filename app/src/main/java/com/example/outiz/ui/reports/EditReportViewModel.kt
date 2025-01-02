package com.example.outiz.ui.reports

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.TimeEntryDao
import com.example.outiz.models.Report
import com.example.outiz.models.TimeEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EditReportViewModel @Inject constructor(
    private val reportDao: ReportDao,
    private val timeEntryDao: TimeEntryDao
) : ViewModel() {

    private val _currentReport = MutableLiveData<Report?>(null)
    val currentReport: LiveData<Report?> = _currentReport

    private val _timeEntries = MutableLiveData<List<TimeEntry>>(emptyList())
    val timeEntries: LiveData<List<TimeEntry>> = _timeEntries

    private val _siteName = MutableLiveData<String>("")
    val siteName: LiveData<String> = _siteName

    private val _description = MutableLiveData<String>("")
    val description: LiveData<String> = _description

    private val _date = MutableLiveData(LocalDateTime.now())
    val date: LiveData<LocalDateTime> = _date

    private val _caller = MutableLiveData<String>("")
    val caller: LiveData<String> = _caller

    private val _callDate = MutableLiveData(LocalDateTime.now())
    val callDate: LiveData<LocalDateTime> = _callDate

    private val _callReason = MutableLiveData<String>("")
    val callReason: LiveData<String> = _callReason

    private val _hasTimeTracking = MutableLiveData(true)
    val hasTimeTracking: LiveData<Boolean> = _hasTimeTracking

    private val _hasPhotos = MutableLiveData(true)
    val hasPhotos: LiveData<Boolean> = _hasPhotos

    fun loadReport(reportId: Long) {
        viewModelScope.launch {
            val report = reportDao.getReportById(reportId)
            report?.let { loadedReport ->
                _currentReport.value = loadedReport
                updateFieldsFromReport(loadedReport)
                _timeEntries.value = timeEntryDao.getTimeEntriesForReport(reportId)
            }
        }
    }

    private fun updateFieldsFromReport(report: Report) {
        _siteName.value = report.siteName
        _description.value = report.description
        _date.value = report.date
        _caller.value = report.caller
        _callDate.value = report.callDate
        _callReason.value = report.callReason
        _hasTimeTracking.value = report.hasTimeTracking
        _hasPhotos.value = report.hasPhotos
    }

    fun updateSiteName(name: String) {
        _siteName.value = name
    }

    fun updateDescription(description: String) {
        _description.value = description
    }

    fun updateDate(date: LocalDateTime) {
        _date.value = date
    }

    fun updateCaller(caller: String) {
        _caller.value = caller
    }

    fun updateCallDate(callDate: LocalDateTime) {
        _callDate.value = callDate
    }

    fun updateCallReason(reason: String) {
        _callReason.value = reason
    }

    fun updateHasTimeTracking(hasTimeTracking: Boolean) {
        _hasTimeTracking.value = hasTimeTracking
    }

    fun updateHasPhotos(hasPhotos: Boolean) {
        _hasPhotos.value = hasPhotos
    }

    fun saveReport() {
        viewModelScope.launch {
            val report = Report(
                id = currentReport.value?.id ?: 0,
                siteName = siteName.value ?: "",
                description = description.value ?: "",
                date = date.value ?: LocalDateTime.now(),
                caller = caller.value ?: "",
                callDate = callDate.value ?: LocalDateTime.now(),
                callReason = callReason.value ?: "",
                hasTimeTracking = hasTimeTracking.value ?: true,
                hasPhotos = hasPhotos.value ?: true
            )
            reportDao.insert(report)
        }
    }
}