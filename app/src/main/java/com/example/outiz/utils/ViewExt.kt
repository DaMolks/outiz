package com.example.outiz.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    action: String? = null,
    actionListener: (() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, message, duration)
    if (action != null && actionListener != null) {
        snackbar.setAction(action) { actionListener.invoke() }
    }
    snackbar.show()
}