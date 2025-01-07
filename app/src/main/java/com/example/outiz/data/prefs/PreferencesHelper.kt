package com.example.outiz.data.prefs

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesHelper @Inject constructor(
    private val prefs: SharedPreferences
) {
    fun isTechnicianProfileCreated(): Boolean =
        prefs.getBoolean(PREF_TECHNICIAN_PROFILE, false)

    fun setTechnicianProfileCreated(isCreated: Boolean) =
        prefs.edit().putBoolean(PREF_TECHNICIAN_PROFILE, isCreated).apply()

    companion object {
        private const val PREF_TECHNICIAN_PROFILE = "technician_profile_created"
    }
}