package com.example.outiz.ui.sites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.OutizDatabase
import com.example.outiz.models.Site
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SitesViewModel(application: Application) : AndroidViewModel(application) {
    private val siteDao = OutizDatabase.getDatabase(application).siteDao()
    val sites = siteDao.getAllSites().asLiveData()

    fun deleteSite(site: Site) {
        viewModelScope.launch(Dispatchers.IO) {
            siteDao.deleteSite(site)
        }
    }
}