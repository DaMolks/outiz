package com.example.outiz.ui.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.outiz.databinding.DialogAddTimeEntryBinding
import com.example.outiz.models.TimeEntry
import com.example.outiz.ui.reports.ReportViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.LocalDateTime
import java.time.Duration
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
        
        setupTimePicker()
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

    private fun setupTimePicker() {
        binding?.durationInput?.setOnClickListener {
            val calendar = Calendar.getInstance()
            if (timeEntry != null) {
                val duration = timeEntry!!.duration
                calendar.set(Calendar.HOUR_OF_DAY, (duration / 60).toInt())
                calendar.set(Calendar.MINUTE, (duration % 60).toInt())
            }
            
            TimePickerDialog(
                requireContext(),
                { _, hour, minute ->
                    val durationMinutes = hour * 60 + minute
                    binding?.durationInput?.setText(formatDuration(durationMinutes))
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

    private fun populateFields(entry: TimeEntry) {
        binding?.apply {
            durationInput.setText(formatDuration(entry.duration))
        }
    }

    private fun saveTimeEntry() {
        val duration = parseDuration(binding?.durationInput?.text.toString())
        val now = LocalDateTime.now()
        val entry = TimeEntry(
            reportId = reportId,
            date = Date(),
            duration = duration,
            startTime = now,
            endTime = now.plusMinutes(duration.toLong()),
            description = binding?.durationInput?.text.toString(),
            taskType = "Intervention"
        )

        if (timeEntry == null) {
            viewModel.addTimeEntry(entry)
        } else {
            // Implémentez la mise à jour si nécessaire
        }
    }

    private fun formatDuration(minutes: Int): String {
        return String.format("%02d:%02d", minutes / 60, minutes % 60)
    }

    private fun parseDuration(durationStr: String): Int {
        val parts = durationStr.split(":")
        return if (parts.size == 2) {
            parts[0].toInt() * 60 + parts[1].toInt()
        } else {
            0
        }
    }

    companion object {
        private const val REPORT_ID_KEY = "reportId"
        private const val TIME_ENTRY_KEY = "timeEntry"
    }
}