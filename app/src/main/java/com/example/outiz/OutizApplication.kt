package com.example.outiz

import android.app.Application
import com.example.outiz.data.OutizDatabase
import com.example.outiz.utils.AppPreferenceManager

class OutizApplication : Application() {
    val database by lazy { OutizDatabase.getDatabase(this) }
    val preferenceManager by lazy { AppPreferenceManager(this) }
}