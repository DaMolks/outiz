package com.example.outiz.ui.reports

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.repository.ReportRepository
import com.example.outiz.models.Report
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val repository: ReportRepository
) : ViewModel() {

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