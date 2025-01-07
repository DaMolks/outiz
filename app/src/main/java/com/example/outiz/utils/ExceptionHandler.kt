package com.example.outiz.utils

import timber.log.Timber

class ExceptionHandler private constructor() {
    companion object {
        fun handleException(e: Throwable) {
            Timber.e(e, "Unhandled exception")
            // Add additional error handling logic here
        }
    }
}