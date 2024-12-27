package com.example.outiz.ui.reports

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.outiz.data.OutizDatabase
import com.example.outiz.models.Report

class ReportViewModel(application: Application) : AndroidViewModel(application) {
    private val reportDao = OutizDatabase.getDatabase(application).reportDao()
    val currentReport = MutableLiveData<Report?>(null)
    var hasTimeTracking = false
    var hasPhotos = false
}