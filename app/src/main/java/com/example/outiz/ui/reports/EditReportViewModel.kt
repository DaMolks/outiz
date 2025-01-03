package com.example.outiz.ui.reports

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.repository.ReportRepository
import com.example.outiz.models.Report
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EditReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : ViewModel() {

    private val _report = MutableLiveData<Report?>(null)
    val report: LiveData<Report?> = _report

    private val _siteName = MutableLiveData<String>("")
    val siteName: LiveData<String> = _siteName

    private val _description = MutableLiveData<String>("")
    val description: LiveData<String> = _description

    private val _caller = MutableLiveData<String>("")
    val caller: LiveData<String> = _caller

    private val _callReason = MutableLiveData<String>("")
    val callReason: LiveData<String> = _callReason

    private val _callDate = MutableLiveData<LocalDateTime>(LocalDateTime.now())
    val callDate: LiveData<LocalDateTime> = _callDate

    private val _hasTimeTracking = MutableLiveData(false)
    val hasTimeTracking: LiveData<Boolean> = _hasTimeTracking

    private val _hasPhotos = MutableLiveData(false)
    val hasPhotos: LiveData<Boolean> = _hasPhotos

    private val _reportSaved = MutableLiveData(false)
    val reportSaved: LiveData<Boolean> = _reportSaved

    fun loadReport(reportId: Long) {
        viewModelScope.launch {
            val loadedReport = reportRepository.getReportById(reportId)
            _report.value = loadedReport
            loadedReport?.let {
                _siteName.value = it.siteName
                _description.value = it.description
                _hasTimeTracking.value = it.hasTimeTracking
                _hasPhotos.value = it.hasPhotos
            }
        }
    }

    fun saveReport(siteName: String, description: String) {
        val currentReport = _report.value ?: Report(
            siteName = siteName,
            description = description,
            date = LocalDateTime.now(),
            hasTimeTracking = _hasTimeTracking.value ?: false,
            hasPhotos = _hasPhotos.value ?: false
        )

        viewModelScope.launch {
            if (currentReport.id == 0L) {
                reportRepository.insert(currentReport)
            } else {
                reportRepository.update(currentReport)
            }
            _reportSaved.value = true
        }
    }

    fun updateSiteName(siteName: String) {
        _siteName.value = siteName
    }

    fun updateDescription(description: String) {
        _description.value = description
    }

    fun updateCaller(caller: String) {
        _caller.value = caller
    }

    fun updateCallReason(reason: String) {
        _callReason.value = reason
    }

    fun updateHasTimeTracking(isEnabled: Boolean) {
        _hasTimeTracking.value = isEnabled
    }

    fun updateHasPhotos(isEnabled: Boolean) {
        _hasPhotos.value = isEnabled
    }
}