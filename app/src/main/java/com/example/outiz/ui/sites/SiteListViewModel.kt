package com.example.outiz.ui.sites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.example.outiz.data.OutizDatabase

class SiteListViewModel(application: Application) : AndroidViewModel(application) {
    private val siteDao = OutizDatabase.getDatabase(application).siteDao()
    val sites = siteDao.getAllSites().asLiveData()
}
