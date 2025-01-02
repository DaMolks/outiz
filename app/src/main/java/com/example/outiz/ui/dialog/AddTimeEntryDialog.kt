package com.example.outiz.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.example.outiz.R
import com.example.outiz.databinding.DialogAddTimeEntryBinding

class AddTimeEntryDialog(
    context: Context,
    private val onSave: (description: String, duration: Int) -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogAddTimeEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        
        binding = DialogAddTimeEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            val description = binding.etDescription.text.toString().trim()
            val duration = binding.etDuration.text.toString().toIntOrNull() ?: 0
            
            if (validateInputs(description, duration)) {
                onSave(description, duration)
                dismiss()
            }
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun validateInputs(description: String, duration: Int): Boolean {
        var isValid = true

        if (description.isBlank()) {
            binding.etDescription.error = context.getString(R.string.error_description_required)
            isValid = false
        }

        if (duration <= 0) {
            binding.etDuration.error = context.getString(R.string.error_invalid_duration)
            isValid = false
        }

        return isValid
    }
}