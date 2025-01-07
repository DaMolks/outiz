package com.example.outiz.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.dao.SiteDao
import com.example.outiz.models.Site
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SiteViewModel @Inject constructor(
    private val siteDao: SiteDao
) : ViewModel() {

    val allSites: LiveData<List<Site>> = siteDao.getAllSites()

    fun insertSite(site: Site) = viewModelScope.launch {
        siteDao.insertSite(site)
    }

    fun updateSite(site: Site) = viewModelScope.launch {
        siteDao.updateSite(site)
    }

    fun deleteSite(site: Site) = viewModelScope.launch {
        siteDao.deleteSite(site)
    }

    suspend fun getSiteById(id: Long): Site? {
        return siteDao.getSiteById(id)
    }
}