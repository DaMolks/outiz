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
class ReportDetailsViewModel @Inject constructor(
    private val reportDao: ReportDao
) : ViewModel() {

    private val _report = MutableLiveData<Report?>(null)
    val report: LiveData<Report?> = _report

    fun loadReport(reportId: Long) {
        viewModelScope.launch {
            _report.value = reportDao.getReportById(reportId)
        }
    }
}