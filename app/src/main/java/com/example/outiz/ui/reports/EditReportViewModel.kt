package com.example.outiz.ui.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.SiteDao
import com.example.outiz.models.Report
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class EditReportViewModel(
    private val reportDao: ReportDao,
    private val siteDao: SiteDao
) : ViewModel() {

    fun updateReport(
        id: Long,
        siteId: Long,
        date: LocalDateTime,
        description: String,
        status: String,
        caller: String? = null,
        callDate: LocalDateTime? = null,
        callReason: String? = null,
        actions: String? = null,
        photosPaths: List<String>? = null,
        isTimeTrackingEnabled: Boolean = false,
        isPhotosEnabled: Boolean = false
    ) = viewModelScope.launch {
        val report = Report(
            id = id,
            siteId = siteId,
            date = date,
            description = description,
            status = status,
            caller = caller,
            callDate = callDate,
            callReason = callReason,
            actions = actions,
            photosPaths = photosPaths,
            isTimeTrackingEnabled = isTimeTrackingEnabled,
            isPhotosEnabled = isPhotosEnabled
        )
        reportDao.insert(report)
    }

    class Factory(private val reportDao: ReportDao, private val siteDao: SiteDao) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditReportViewModel(reportDao, siteDao) as T
        }
    }
}