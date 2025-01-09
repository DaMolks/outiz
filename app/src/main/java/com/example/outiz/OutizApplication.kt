package com.example.outiz

import android.app.Application
import com.example.outiz.utils.Logger
dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OutizApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.init()
    }
}