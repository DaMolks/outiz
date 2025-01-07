package com.example.outiz.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

class ErrorHandler(private val view: View) {
    
    fun handleError(error: String, action: (() -> Unit)? = null) {
        if (action != null) {
            view.showSnackbarWithAction(
                message = error,
                actionText = "Retry",
                action = action
            )
        } else {
            view.showSnackbar(error)
        }
    }
    
    fun showNetworkError(action: () -> Unit) {
        view.showSnackbarWithAction(
            message = "Network error",
            actionText = "Retry",
            action = action
        )
    }
}