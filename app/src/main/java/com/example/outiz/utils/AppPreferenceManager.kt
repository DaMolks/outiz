package com.example.outiz.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferenceManager @Inject constructor(context: Context) {
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        private const val KEY_TECHNICIAN_ID = "technician_id"
        private const val KEY_TECHNICIAN_NAME = "technician_name"
    }

    var technicianId: Long
        get() = prefs.getLong(KEY_TECHNICIAN_ID, -1L)
        set(value) = prefs.edit().putLong(KEY_TECHNICIAN_ID, value).apply()

    var technicianName: String?
        get() = prefs.getString(KEY_TECHNICIAN_NAME, null)
        set(value) = prefs.edit().putString(KEY_TECHNICIAN_NAME, value).apply()

    fun hasTechnician(): Boolean {
        return technicianId != -1L && !technicianName.isNullOrBlank()
    }

    fun clearTechnician() {
        prefs.edit()
            .remove(KEY_TECHNICIAN_ID)
            .remove(KEY_TECHNICIAN_NAME)
            .apply()
    }
}