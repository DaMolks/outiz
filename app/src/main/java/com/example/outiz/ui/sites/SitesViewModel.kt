package com.example.outiz.ui.sites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.OutizDatabase
import com.example.outiz.models.Site
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SitesViewModel(application: Application) : AndroidViewModel(application) {

    private val database = OutizDatabase.getDatabase(application)
    private val siteDao = database.siteDao()

    private val _sites = MutableLiveData<List<Site>>()
    val sites: LiveData<List<Site>> = _sites

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadSites()
    }

    private fun loadSites() {
        viewModelScope.launch {
            siteDao.getAllSites()
                .catch { e ->
                    _error.value = e.message
                }
                .collect { siteList ->
                    _sites.value = siteList
                }
        }
    }

    fun deleteSite(site: Site) {
        viewModelScope.launch {
            try {
                siteDao.deleteSite(site)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun searchSites(query: String) {
        viewModelScope.launch {
            siteDao.searchSites(query)
                .catch { e ->
                    _error.value = e.message
                }
                .collect { siteList ->
                    _sites.value = siteList
                }
        }
    }

    fun clearError() {
        _error.value = null
    }
}