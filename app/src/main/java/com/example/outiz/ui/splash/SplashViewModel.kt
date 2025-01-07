package com.example.outiz.ui.splash

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.outiz.ui.base.BaseViewModel
import com.example.outiz.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : BaseViewModel() {

    private val _isTechnicianProfileCreated = MutableLiveData<Boolean>()
    val isTechnicianProfileCreated: LiveData<Boolean> = _isTechnicianProfileCreated

    init {
        checkTechnicianProfile()
    }

    private fun checkTechnicianProfile() {
        _isTechnicianProfileCreated.value = sharedPreferences.getBoolean(Constants.PREF_TECHNICIAN_PROFILE, false)
    }
}