package com.example.outiz.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.dao.TechnicianDao
import com.example.outiz.models.Technician
import com.example.outiz.utils.AppPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TechnicianProfileViewModel @Inject constructor(
    private val technicianDao: TechnicianDao,
    private val preferenceManager: AppPreferenceManager
) : ViewModel() {

    private val _firstName = MutableLiveData<String>()
    val firstName: LiveData<String> = _firstName

    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String> = _lastName

    private val _employeeId = MutableLiveData<String>()
    val employeeId: LiveData<String> = _employeeId

    private val _sector = MutableLiveData<String>()
    val sector: LiveData<String> = _sector

    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> = _saved

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadTechnician()
    }

    private fun loadTechnician() {
        val technicianId = preferenceManager.technicianId
        if (technicianId != -1L) {
            viewModelScope.launch {
                technicianDao.getTechnicianById(technicianId).collect { technician ->
                    technician?.let { updateFields(it) }
                }
            }
        }
    }

    private fun updateFields(technician: Technician) {
        _firstName.value = technician.firstName
        _lastName.value = technician.lastName
        _employeeId.value = technician.employeeId
        _sector.value = technician.sector
    }

    fun updateFirstName(name: String) {
        _firstName.value = name
    }

    fun updateLastName(name: String) {
        _lastName.value = name
    }

    fun updateEmployeeId(id: String) {
        _employeeId.value = id
    }

    fun updateSector(sector: String) {
        _sector.value = sector
    }

    fun saveTechnician() {
        val firstName = _firstName.value?.trim()
        val lastName = _lastName.value?.trim()
        val employeeId = _employeeId.value?.trim()
        val sector = _sector.value?.trim()

        if (firstName.isNullOrBlank() || lastName.isNullOrBlank() || 
            employeeId.isNullOrBlank() || sector.isNullOrBlank()) {
            _error.value = "fill_required_fields"
            return
        }

        viewModelScope.launch {
            try {
                val technician = Technician(
                    id = if (preferenceManager.hasTechnician()) preferenceManager.technicianId else 0,
                    firstName = firstName,
                    lastName = lastName,
                    employeeId = employeeId,
                    sector = sector
                )

                val techId = technicianDao.insert(technician)
                preferenceManager.technicianId = techId
                _saved.value = true
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}