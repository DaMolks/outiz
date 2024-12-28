package com.example.outiz.utils

import android.content.Context
import androidx.preference.PreferenceManager as AndroidXPreferenceManager

class PreferenceManager(context: Context) {
    private val sharedPreferences = AndroidXPreferenceManager.getDefaultSharedPreferences(context)

    var isTechnicianProfileCreated: Boolean
        get() = sharedPreferences.getBoolean(KEY_TECHNICIAN_PROFILE, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_TECHNICIAN_PROFILE, value).apply()

    companion object {
        private const val KEY_TECHNICIAN_PROFILE = "technician_profile_created"
    }
}