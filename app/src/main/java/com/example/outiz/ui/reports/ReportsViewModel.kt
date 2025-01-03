package com.example.outiz.ui.reports

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.repository.ReportRepository
import com.example.outiz.models.Report
import kotlinx.coroutines.flow.Flow

class ReportsViewModel : ViewModel() {
    private val repository = ReportRepository() // Assuming this exists

    val reports: LiveData<List<Report>> = repository.getAllReports()
        .asLiveData(viewModelScope.coroutineContext)

    fun addReport(report: Report) {
        viewModelScope.launch {
            repository.insert(report)
        }
    }

    fun deleteReport(report: Report) {
        viewModelScope.launch {
            repository.delete(report)
        }
    }
}