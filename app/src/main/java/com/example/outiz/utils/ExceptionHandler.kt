package com.example.outiz.utils

import android.util.Log
import com.example.outiz.BuildConfig
import timber.log.Timber
kotlin.system.exitProcess

class ExceptionHandler : Thread.UncaughtExceptionHandler {
    private val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        try {
            Timber.e(throwable)
            if (!BuildConfig.DEBUG) {
                Log.e("Outiz", "Fatal error:", throwable)
                // Ici, on pourrait envoyer le rapport à un service de crash reporting
            }
        } catch (e: Exception) {
            // Assurons-nous que nous ne créons pas une boucle d'exceptions
            Log.e("Outiz", "Error reporting crash", e)
        } finally {
            defaultHandler?.uncaughtException(thread, throwable) ?: exitProcess(1)
        }
    }
}