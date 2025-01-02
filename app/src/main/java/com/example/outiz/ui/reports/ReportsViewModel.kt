package com.example.outiz.ui.reports

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.models.Report
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val reportDao: ReportDao
) : ViewModel() {

    private val _reports = MutableLiveData<List<Report>>()
    val reports: LiveData<List<Report>> = _reports

    init {
        loadReports()
    }

    private fun loadReports() {
        viewModelScope.launch {
            _reports.value = reportDao.getAllReports()
        }
    }

    fun deleteReport(report: Report) {
        viewModelScope.launch {
            reportDao.delete(report)
            loadReports()
        }
    }

    fun addReport(report: Report) {
        viewModelScope.launch {
            reportDao.insert(report)
            loadReports()
        }
    }
}