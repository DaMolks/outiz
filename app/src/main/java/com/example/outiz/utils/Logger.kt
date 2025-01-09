package com.example.outiz.utils

import timber.log.Timber

object Logger {
    fun init() {
        Timber.plant(Timber.DebugTree())
    }

    fun d(message: String, vararg args: Any) {
        Timber.d(message, *args)
    }

    fun i(message: String, vararg args: Any) {
        Timber.i(message, *args)
    }

    fun e(throwable: Throwable, message: String? = null) {
        if (message != null) {
            Timber.e(throwable, message)
        } else {
            Timber.e(throwable)
        }
    }

    fun w(message: String, vararg args: Any) {
        Timber.w(message, *args)
    }
}