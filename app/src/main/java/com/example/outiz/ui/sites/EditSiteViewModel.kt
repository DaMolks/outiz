package com.example.outiz.ui.sites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.OutizDatabase
import com.example.outiz.models.Site
import kotlinx.coroutines.launch
import java.util.UUID

class EditSiteViewModel(application: Application) : AndroidViewModel(application) {

    private val database = OutizDatabase.getDatabase(application)
    private val siteDao = database.siteDao()

    private var currentSiteId: String? = null

    private val _site = MutableLiveData<Site?>()
    val site: LiveData<Site?> = _site

    private val _saved = MutableLiveData<Boolean>(false)
    val saved: LiveData<Boolean> = _saved

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadSite(siteId: String) {
        currentSiteId = siteId
        viewModelScope.launch {
            try {
                val site = siteDao.getSiteById(siteId)
                _site.value = site
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun saveSite(
        name: String,
        codeS: String,
        address: String,
        clientName: String
    ) {
        if (!validateInputs(name, codeS, address, clientName)) {
            return
        }

        viewModelScope.launch {
            try {
                val site = Site(
                    id = currentSiteId ?: UUID.randomUUID().toString(),
                    name = name,
                    codeS = codeS,
                    address = address,
                    clientName = clientName
                )

                siteDao.insertSite(site)
                _saved.value = true
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    private fun validateInputs(
        name: String,
        codeS: String,
        address: String,
        clientName: String
    ): Boolean {
        return name.isNotBlank() && 
               codeS.isNotBlank() && 
               address.isNotBlank() && 
               clientName.isNotBlank()
    }

    fun clearError() {
        _error.value = null
    }
}