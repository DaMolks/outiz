package com.example.outiz.ui.reports

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.repository.ReportRepository
import com.example.outiz.models.Report
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EditReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : ViewModel() {

    private val _report = MutableLiveData<Report?>(null)
    val report: LiveData<Report?> = _report

    private val _hasTimeTracking = MutableLiveData(false)
    val hasTimeTracking: LiveData<Boolean> = _hasTimeTracking

    private val _hasPhotos = MutableLiveData(false)
    val hasPhotos: LiveData<Boolean> = _hasPhotos

    private val _reportSaved = MutableLiveData(false)
    val reportSaved: LiveData<Boolean> = _reportSaved

    fun loadReport(reportId: Long) {
        viewModelScope.launch {
            _report.value = reportRepository.getReportById(reportId)
            _hasTimeTracking.value = _report.value?.hasTimeTracking ?: false
            _hasPhotos.value = _report.value?.hasPhotos ?: false
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

    fun updateHasTimeTracking(isEnabled: Boolean) {
        _hasTimeTracking.value = isEnabled
    }

    fun updateHasPhotos(isEnabled: Boolean) {
        _hasPhotos.value = isEnabled
    }
}