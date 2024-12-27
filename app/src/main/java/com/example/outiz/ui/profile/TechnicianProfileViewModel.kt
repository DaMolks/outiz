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

class TechnicianProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val database = OutizDatabase.getDatabase(application)
    private val technicianDao = database.technicianDao()

    private val _saveSuccess = MutableLiveData<Boolean>()
    val saveSuccess: LiveData<Boolean> = _saveSuccess

    fun saveTechnician(firstName: String, lastName: String, sector: String) {
        viewModelScope.launch {
            try {
                val technician = Technician(
                    id = "1", // ID fixe car un seul technicien
                    firstName = firstName,
                    lastName = lastName,
                    sector = sector
                )
                technicianDao.insert(technician)

                // Marquer dans les préférences que le technicien est créé
                val prefs = PreferenceManager.getDefaultSharedPreferences(getApplication())
                prefs.edit().putBoolean("has_technician", true).apply()

                _saveSuccess.value = true
            } catch (e: Exception) {
                _saveSuccess.value = false
            }
        }
    }
}