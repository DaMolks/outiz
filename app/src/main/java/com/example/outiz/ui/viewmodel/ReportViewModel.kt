package com.example.outiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.models.Report
import com.example.outiz.utils.AppPreferenceManager
import kotlinx.coroutines.launch

class ReportViewModel(
    private val reportDao: ReportDao,
    private val preferenceManager: AppPreferenceManager
) : ViewModel() {

    val allReports = reportDao.getAllReports().asLiveData()

    fun insertReport(report: Report) = viewModelScope.launch {
        reportDao.insert(report)
    }

    fun deleteReport(report: Report) = viewModelScope.launch {
        reportDao.delete(report)
    }

    class Factory(private val reportDao: ReportDao, private val preferenceManager: AppPreferenceManager) : 
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ReportViewModel(reportDao, preferenceManager) as T
        }
    }
}