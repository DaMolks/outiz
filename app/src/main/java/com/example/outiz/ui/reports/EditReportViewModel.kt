package com.example.outiz.ui.reports

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.OutizDatabase
import com.example.outiz.models.Report
import com.example.outiz.models.ReportWithDetails
import com.example.outiz.models.Site
import com.example.outiz.models.TimeEntry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

class EditReportViewModel(application: Application) : AndroidViewModel(application) {

    private val database = OutizDatabase.getDatabase(application)
    private val reportDao = database.reportDao()
    private val siteDao = database.siteDao()

    private val _sites = MutableLiveData<List<Site>>()
    val sites: LiveData<List<Site>> = _sites

    private val _report = MutableLiveData<ReportWithDetails?>()
    val report: LiveData<ReportWithDetails?> = _report

    private val _timeEntries = MutableLiveData<List<TimeEntry>>()
    val timeEntries: LiveData<List<TimeEntry>> = _timeEntries

    private val _photosPaths = MutableLiveData<List<String>>()
    val photosPaths: LiveData<List<String>> = _photosPaths

    private val _saved = MutableLiveData<Boolean>(false)
    val saved: LiveData<Boolean> = _saved

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadSites()
    }

    private fun loadSites() {
        viewModelScope.launch {
            try {
                val sitesList = siteDao.getAllSites().first()
                _sites.value = sitesList
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun loadReport(reportId: String) {
        viewModelScope.launch {
            try {
                val report = reportDao.getReportById(reportId)
                _report.value = report
                report?.let {
                    _timeEntries.value = it.timeEntries
                    _photosPaths.value = it.report.photosPaths ?: emptyList()
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun saveReport(
        siteId: String,
        callDate: Date,
        callReason: String,
        caller: String,
        isTimeTrackingEnabled: Boolean,
        isPhotosEnabled: Boolean
    ) {
        viewModelScope.launch {
            try {
                val report = Report(
                    id = _report.value?.report?.id ?: UUID.randomUUID().toString(),
                    siteId = siteId,
                    callDate = callDate,
                    callReason = callReason,
                    caller = caller,
                    actions = emptyList(), // À mettre à jour plus tard
                    photosPaths = if (isPhotosEnabled) _photosPaths.value else null,
                    isTimeTrackingEnabled = isTimeTrackingEnabled,
                    isPhotosEnabled = isPhotosEnabled
                )

                reportDao.updateReportWithTimeEntries(
                    report = report,
                    timeEntries = if (isTimeTrackingEnabled) _timeEntries.value ?: emptyList() else emptyList()
                )

                _saved.value = true
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun addTimeEntry(
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        description: String,
        taskType: String
    ) {
        val currentReport = _report.value?.report ?: return
        val duration = java.time.Duration.between(startTime, endTime).toMinutes().toInt()

        val newEntry = TimeEntry(
            reportId = currentReport.id,
            date = Date(),
            duration = duration,
            startTime = startTime,
            endTime = endTime,
            description = description,
            taskType = taskType
        )

        val currentEntries = _timeEntries.value.orEmpty().toMutableList()
        currentEntries.add(newEntry)
        _timeEntries.value = currentEntries
    }

    fun removeTimeEntry(timeEntry: TimeEntry) {
        val currentEntries = _timeEntries.value.orEmpty().toMutableList()
        currentEntries.remove(timeEntry)
        _timeEntries.value = currentEntries
    }

    fun addPhoto(path: String) {
        val currentPaths = _photosPaths.value.orEmpty().toMutableList()
        currentPaths.add(path)
        _photosPaths.value = currentPaths
    }

    fun removePhoto(path: String) {
        val currentPaths = _photosPaths.value.orEmpty().toMutableList()
        currentPaths.remove(path)
        _photosPaths.value = currentPaths
    }

    fun clearError() {
        _error.value = null
    }
}