package com.example.outiz.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.outiz.data.dao.TechnicianDao
import com.example.outiz.models.Technician
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TechnicianViewModel @Inject constructor(
    private val technicianDao: TechnicianDao
) : ViewModel() {

    val currentTechnician: LiveData<Technician?> = technicianDao.getCurrentTechnician()

    fun insertTechnician(technician: Technician) = viewModelScope.launch {
        technicianDao.insertTechnician(technician)
    }

    fun updateTechnician(technician: Technician) = viewModelScope.launch {
        technicianDao.updateTechnician(technician)
    }

    fun deleteTechnician(technician: Technician) = viewModelScope.launch {
        technicianDao.deleteTechnician(technician)
    }
}