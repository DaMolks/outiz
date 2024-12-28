package com.example.outiz.utils

import android.content.Context
import androidx.preference.PreferenceManager

class AppPreferenceManager(context: Context) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var isTechnicianProfileCreated: Boolean
        get() = preferences.getBoolean(Constants.PREF_TECHNICIAN_PROFILE, false)
        set(value) = preferences.edit().putBoolean(Constants.PREF_TECHNICIAN_PROFILE, value).apply()
}