package com.example.outiz.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.outiz.data.OutizDatabase
import com.example.outiz.models.Technician
import kotlinx.coroutines.launch
import java.util.UUID

class TechnicianProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val database = OutizDatabase.getDatabase(application)
    private val technicianDao = database.technicianDao()

    private val _profileCreated = MutableLiveData<Boolean>()
    val profileCreated: LiveData<Boolean> = _profileCreated

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun createProfile(
        firstName: String,
        lastName: String,
        sector: String,
        identifier: String,
        supervisor: String
    ) {
        if (validateInputs(firstName, lastName, sector, identifier, supervisor)) {
            viewModelScope.launch {
                try {
                    val technician = Technician(
                        id = UUID.randomUUID().toString(),
                        firstName = firstName,
                        lastName = lastName,
                        sector = sector,
                        identifier = identifier,
                        supervisor = supervisor
                    )

                    technicianDao.insertTechnician(technician)

                    // Sauvegarder l'état dans les préférences
                    val prefs = PreferenceManager.getDefaultSharedPreferences(getApplication())
                    prefs.edit()
                        .putBoolean("has_technician", true)
                        .putString("technician_id", technician.id)
                        .apply()

                    _profileCreated.value = true
                } catch (e: Exception) {
                    _error.value = e.message
                }
            }
        }
    }

    private fun validateInputs(
        firstName: String,
        lastName: String,
        sector: String,
        identifier: String,
        supervisor: String
    ): Boolean {
        return firstName.isNotBlank() &&
                lastName.isNotBlank() &&
                sector.isNotBlank() &&
                identifier.isNotBlank() &&
                supervisor.isNotBlank()
    }

    fun clearError() {
        _error.value = null
    }
}