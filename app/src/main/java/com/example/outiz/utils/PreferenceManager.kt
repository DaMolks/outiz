package com.example.outiz.utils

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class AppPreferenceManager(context: Context) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var technicianProfileCreated: Boolean
        get() = preferences.getBoolean(KEY_TECHNICIAN_PROFILE, false)
        set(value) = preferences.edit { putBoolean(KEY_TECHNICIAN_PROFILE, value) }

    companion object {
        private const val KEY_TECHNICIAN_PROFILE = "technician_profile_created"
    }
}