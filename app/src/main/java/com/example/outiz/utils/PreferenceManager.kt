package com.example.outiz.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class PreferenceHelper(context: Context) {
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun setTechnicianId(id: Long) {
        prefs.edit().putLong(PREF_TECHNICIAN_ID, id).apply()
    }

    fun getTechnicianId(): Long {
        return prefs.getLong(PREF_TECHNICIAN_ID, -1)
    }

    fun hasTechnician(): Boolean {
        return getTechnicianId() != -1L
    }

    companion object {
        private const val PREF_TECHNICIAN_ID = "pref_technician_id"
    }
}