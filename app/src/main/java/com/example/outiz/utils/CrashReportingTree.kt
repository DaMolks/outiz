package com.example.outiz.utils

import android.util.Log
import timber.log.Timber

class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR) {
            // Ici, on pourrait envoyer les logs à un service comme Firebase Crashlytics
            Logger.e(message, t)
        }
    }
}