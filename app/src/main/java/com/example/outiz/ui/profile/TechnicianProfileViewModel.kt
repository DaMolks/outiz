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
    private val prefs = PreferenceManager.getDefaultSharedPreferences(application)

    private val _saveSuccess = MutableLiveData<Boolean>()
    val saveSuccess: LiveData<Boolean> = _saveSuccess

    fun saveTechnician(firstName: String, lastName: String, sector: String) {
        viewModelScope.launch {
            val technician = Technician(
                id = UUID.randomUUID().toString(),
                firstName = firstName,
                lastName = lastName,
                sector = sector
            )
            val insertedId = technicianDao.insert(technician)
            if (insertedId > 0) {
                // Stocker l'ID du technicien dans les préférences
                prefs.edit().putString("technician_id", technician.id).apply()
                _saveSuccess.value = true
            } else {
                _saveSuccess.value = false
            }
        }
    }
}