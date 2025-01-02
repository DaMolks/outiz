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

    fun loadReport(reportId: Long) {
        viewModelScope.launch {
            val report = reportDao.getReportById(reportId)
            _currentReport.value = report
            _timeEntries.value = report?.let { timeEntryDao.getTimeEntriesForReport(reportId) } ?: emptyList()
            updateFields(report)
        }
    }

    private fun updateFields(report: Report?) {
        _siteName.value = report?.siteName ?: ""
        _description.value = report?.description ?: ""
    }

    fun saveReport(onComplete: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            val report = createReportFromFields()
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
            siteName = siteName.value ?: "",
            description = description.value ?: "",
            date = currentReport.value?.date ?: java.util.Date(),
            caller = currentReport.value?.caller ?: "",
            callDate = currentReport.value?.callDate ?: java.util.Date(),
            callReason = currentReport.value?.callReason ?: "",
            hasTimeTracking = true,
            hasPhotos = true
        )
    }

    fun updateSiteName(name: String) {
        _siteName.value = name
    }

    fun updateDescription(desc: String) {
        _description.value = desc
    }
}