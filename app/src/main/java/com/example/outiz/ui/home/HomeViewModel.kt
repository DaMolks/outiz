package com.example.outiz.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.outiz.R
import com.example.outiz.models.Module
import com.example.outiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {
    private val _modules = MutableLiveData<List<Module>>()
    val modules: LiveData<List<Module>> = _modules

    init {
        loadModules()
    }

    private fun loadModules() {
        val moduleList = listOf(
            Module(R.id.module_reports, R.string.nav_reports, R.drawable.ic_reports),
            Module(R.id.module_sites, R.string.nav_sites, R.drawable.ic_sites),
            Module(R.id.module_settings, R.string.nav_settings, R.drawable.ic_settings)
        )
        _modules.value = moduleList
    }
}