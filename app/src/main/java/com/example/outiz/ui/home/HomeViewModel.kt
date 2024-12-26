package com.example.outiz.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.outiz.R
import com.example.outiz.models.Module

class HomeViewModel : ViewModel() {

    private val _modules = MutableLiveData<List<Module>>().apply {
        value = listOf(
            Module(
                id = "reports",
                title = "Rapports",
                description = "Créer et gérer les rapports d'intervention",
                iconRes = R.drawable.ic_report
            ),
            Module(
                id = "sites",
                title = "Sites",
                description = "Gérer les sites d'intervention",
                iconRes = R.drawable.ic_sites
            )
        )
    }

    val modules: LiveData<List<Module>> = _modules

    private val _navigateToModule = MutableLiveData<String?>()
    val navigateToModule: LiveData<String?> = _navigateToModule

    fun onModuleClicked(module: Module) {
        _navigateToModule.value = module.id
    }

    fun onNavigationHandled() {
        _navigateToModule.value = null
    }
}