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

    private val _currentReport = MutableLiveData<Report?>()
    val currentReport: LiveData<Report?> = _currentReport

    private val _timeEntries = MutableLiveData<List<TimeEntry>>(emptyList())
    val timeEntries: LiveData<List<TimeEntry>> = _timeEntries

    // Form fields
    private val _siteName = MutableLiveData<String>()
    val siteName: LiveData<String> = _siteName

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val _date = MutableLiveData(LocalDateTime.now())
    val date: LiveData<LocalDateTime> = _date

    private val _caller = MutableLiveData<String>()
    val caller: LiveData<String> = _caller

    private val _callDate = MutableLiveData(LocalDateTime.now())
    val callDate: LiveData<LocalDateTime> = _callDate

    private val _callReason = MutableLiveData<String>()
    val callReason: LiveData<String> = _callReason

    private val _hasTimeTracking = MutableLiveData(true)
    val hasTimeTracking: LiveData<Boolean> = _hasTimeTracking

    private val _hasPhotos = MutableLiveData(true)
    val hasPhotos: LiveData<Boolean> = _hasPhotos

    fun loadReport(reportId: Long) {
        viewModelScope.launch {
            val report = reportDao.getReportById(reportId)
            _currentReport.value = report
            report?.let { updateFields(it) }
        }
    }

    private fun updateFields(report: Report) {
        _siteName.value = report.siteName
        _description.value = report.description
        _date.value = report.date
        _caller.value = report.caller
        _callDate.value = report.callDate
        _callReason.value = report.callReason
        _hasTimeTracking.value = report.hasTimeTracking
        _hasPhotos.value = report.hasPhotos
    }

    fun saveReport(onComplete: (Boolean) -> Unit) {
        val report = createReportFromFields()
        viewModelScope.launch {
            try {
                reportDao.insert(report)
                onComplete(true)
            } catch (e: Exception) {
                onComplete(false)
            }
        }
    }

    private fun createReportFromFields(): Report {
        return Report(
            id = currentReport.value?.id ?: 0,
            siteName = siteName.value.orEmpty(),
            description = description.value.orEmpty(),
            date = date.value ?: LocalDateTime.now(),
            caller = caller.value.orEmpty(),
            callDate = callDate.value ?: LocalDateTime.now(),
            callReason = callReason.value.orEmpty(),
            hasTimeTracking = hasTimeTracking.value ?: true,
            hasPhotos = hasPhotos.value ?: true
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

    fun updateCaller(caller: String) {
        _caller.value = caller
    }

    fun updateCallDate(date: LocalDateTime) {
        _callDate.value = date
    }

    fun updateCallReason(reason: String) {
        _callReason.value = reason
    }

    fun updateHasTimeTracking(enabled: Boolean) {
        _hasTimeTracking.value = enabled
    }

    fun updateHasPhotos(enabled: Boolean) {
        _hasPhotos.value = enabled
    }
}