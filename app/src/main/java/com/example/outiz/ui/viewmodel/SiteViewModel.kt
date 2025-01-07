package com.example.outiz.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.repository.SiteRepository
import com.example.outiz.models.Site
import com.example.outiz.ui.base.BaseViewModel
import com.example.outiz.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SiteViewModel @Inject constructor(
    private val repository: SiteRepository
) : BaseViewModel() {

    private val _siteState = MutableLiveData<State<Site>>()
    val siteState: LiveData<State<Site>> = _siteState

    val allSites: LiveData<List<Site>> = repository.allSites

    fun insertSite(site: Site) = viewModelScope.launch {
        setLoading(true)
        try {
            repository.insert(site)
            _siteState.value = State.Success(site)
        } catch (e: Exception) {
            _siteState.value = State.Error(e.message ?: "Error inserting site")
        } finally {
            setLoading(false)
        }
    }

    fun updateSite(site: Site) = viewModelScope.launch {
        setLoading(true)
        try {
            repository.update(site)
            _siteState.value = State.Success(site)
        } catch (e: Exception) {
            _siteState.value = State.Error(e.message ?: "Error updating site")
        } finally {
            setLoading(false)
        }
    }

    fun deleteSite(site: Site) = viewModelScope.launch {
        setLoading(true)
        try {
            repository.delete(site)
            _siteState.value = State.Success(site)
        } catch (e: Exception) {
            _siteState.value = State.Error(e.message ?: "Error deleting site")
        } finally {
            setLoading(false)
        }
    }

    fun getSiteById(id: Long) = viewModelScope.launch {
        setLoading(true)
        try {
            val site = repository.getSiteById(id)
            site?.let {
                _siteState.value = State.Success(it)
            } ?: run {
                _siteState.value = State.Error("Site not found")
            }
        } catch (e: Exception) {
            _siteState.value = State.Error(e.message ?: "Error fetching site")
        } finally {
            setLoading(false)
        }
    }
}