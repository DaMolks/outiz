package com.example.outiz.ui.dialog

import android.app.AlertDialog
import android.content.Context
import com.example.outiz.R

class ConfirmationDialog {
    companion object {
        fun show(
            context: Context,
            title: String,
            message: String,
            positiveButtonText: String = context.getString(R.string.confirm),
            negativeButtonText: String = context.getString(R.string.cancel),
            onConfirm: () -> Unit
        ) {
            AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText) { _, _ -> onConfirm() }
                .setNegativeButton(negativeButtonText) { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }
}