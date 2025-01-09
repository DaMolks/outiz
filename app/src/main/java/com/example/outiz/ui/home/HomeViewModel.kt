package com.example.outiz.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.outiz.R
import com.example.outiz.models.Module
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _modules = MutableLiveData<List<Module>>()
    val modules: LiveData<List<Module>> = _modules

    init {
        loadModules()
    }

    private fun loadModules() {
        val modulesList = listOf(
            Module(
                id = R.string.module_reports,
                title = R.string.module_reports,
                description = R.string.module_reports_desc,
                iconRes = R.drawable.ic_reports
            ),
            Module(
                id = R.string.module_sites,
                title = R.string.module_sites,
                description = R.string.module_sites_desc,
                iconRes = R.drawable.ic_sites
            ),
            Module(
                id = R.string.module_settings,
                title = R.string.module_settings,
                description = R.string.module_settings_desc,
                iconRes = R.drawable.ic_settings
            )
        )
        _modules.value = modulesList
    }
}