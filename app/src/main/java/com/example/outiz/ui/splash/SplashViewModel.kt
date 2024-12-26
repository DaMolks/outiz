package com.example.outiz.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.preference.PreferenceManager

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val _isTechnicianProfileCreated = MutableLiveData<Boolean>()
    val isTechnicianProfileCreated: LiveData<Boolean> = _isTechnicianProfileCreated

    init {
        checkTechnicianProfile()
    }

    private fun checkTechnicianProfile() {
        viewModelScope.launch {
            val prefs = PreferenceManager.getDefaultSharedPreferences(getApplication())
            val hasTechnician = prefs.getBoolean("has_technician", false)
            _isTechnicianProfileCreated.value = hasTechnician
        }
    }
}