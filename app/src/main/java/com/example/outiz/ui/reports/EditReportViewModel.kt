package com.example.outiz.ui.reports

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.repository.ReportRepository
import com.example.outiz.models.Report
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditReportViewModel @Inject constructor(
    private val repository: ReportRepository
) : ViewModel() {

    private val _currentReport = MutableLiveData<Report?>()
    val currentReport: LiveData<Report?> = _currentReport

    private val _saveCompleted = MutableLiveData<Boolean>()
    val saveCompleted: LiveData<Boolean> = _saveCompleted

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadReport(id: Long) {
        viewModelScope.launch {
            repository.getReportById(id).collect { report ->
                _currentReport.value = report
            }
        }
    }

    fun saveReport(
        siteName: String,
        description: String,
        hasTimeTracking: Boolean,
        hasPhotos: Boolean
    ) {
        if (siteName.isBlank() || description.isBlank()) {
            _error.value = "Veuillez remplir tous les champs obligatoires"
            return
        }

        viewModelScope.launch {
            try {
                val report = _currentReport.value?.copy(
                    siteName = siteName,
                    description = description,
                    hasTimeTracking = hasTimeTracking,
                    hasPhotos = hasPhotos,
                    updatedAt = Date()
                ) ?: Report(
                    siteName = siteName,
                    description = description,
                    hasTimeTracking = hasTimeTracking,
                    hasPhotos = hasPhotos,
                    createdAt = Date(),
                    updatedAt = Date()
                )

                if (report.id == 0L) {
                    repository.insertReport(report)
                } else {
                    repository.updateReport(report)
                }

                _saveCompleted.value = true
            } catch (e: Exception) {
                _error.value = "Erreur lors de l'enregistrement : ${e.message}"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}