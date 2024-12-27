package com.example.outiz.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.outiz.databinding.DialogAddTimeEntryBinding
import com.example.outiz.models.TimeEntry
import com.example.outiz.ui.reports.ReportViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.LocalDateTime
import java.util.*

class AddTimeEntryDialog : DialogFragment() {
    private var binding: DialogAddTimeEntryBinding? = null
    private lateinit var viewModel: ReportViewModel
    private var reportId: String = ""
    private var timeEntry: TimeEntry? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ReportViewModel::class.java]
        reportId = arguments?.getString(REPORT_ID_KEY) ?: ""
        timeEntry = arguments?.getParcelable(TIME_ENTRY_KEY)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAddTimeEntryBinding.inflate(layoutInflater)
        
        timeEntry?.let { populateFields(it) }

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(if (timeEntry == null) "Nouvelle entrée de temps" else "Modifier l'entrée de temps")
            .setView(binding?.root)
            .setPositiveButton(if (timeEntry == null) "Ajouter" else "Modifier") { _, _ ->
                saveTimeEntry()
            }
            .setNegativeButton("Annuler", null)
            .create()
    }

    private fun populateFields(entry: TimeEntry) {
        binding?.apply {
            editTextStartTime.setText(entry.startTime.toString())
            editTextEndTime.setText(entry.endTime.toString())
            durationInput.setText(entry.duration.toString())
            editTextDescription.setText(entry.description)
        }
    }

    private fun saveTimeEntry() {
        val startTime = LocalDateTime.parse(binding?.editTextStartTime?.text.toString())
        val endTime = LocalDateTime.parse(binding?.editTextEndTime?.text.toString())
        val duration = binding?.durationInput?.text.toString().toIntOrNull() ?: 0
        val description = binding?.editTextDescription?.text.toString()
        val taskType = binding?.spinnerTaskType?.selectedItem.toString()

        val entry = TimeEntry(
            reportId = reportId,
            date = Date(),
            duration = duration,
            startTime = startTime,
            endTime = endTime,
            description = description,
            taskType = taskType
        )

        if (timeEntry == null) {
            viewModel.addTimeEntry(entry)
        } else {
            // TODO: Implémenter la mise à jour
        }

        dismiss()
    }

    companion object {
        private const val REPORT_ID_KEY = "reportId"
        private const val TIME_ENTRY_KEY = "timeEntry"
    }
}