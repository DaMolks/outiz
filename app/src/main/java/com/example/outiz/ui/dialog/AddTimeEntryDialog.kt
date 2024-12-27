package com.example.outiz.ui.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.outiz.databinding.DialogAddTimeEntryBinding
import com.example.outiz.models.TimeEntry
import com.example.outiz.ui.viewmodel.ReportViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

class AddTimeEntryDialog : DialogFragment() {
    private var binding: DialogAddTimeEntryBinding? = null
    private lateinit var viewModel: ReportViewModel
    private var reportId: Long = 0
    private var timeEntry: TimeEntry? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ReportViewModel::class.java]
        reportId = arguments?.getLong("reportId") ?: 0
        timeEntry = arguments?.getParcelable("timeEntry")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAddTimeEntryBinding.inflate(layoutInflater)
        
        setupTimePickers()
        setupOwnerSwitch()
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

    private fun setupTimePickers() {
        binding?.arrivalTimePicker?.setOnClickListener {
            showTimePickerDialog { time ->
                binding?.arrivalTimePicker?.setText(time)
            }
        }

        binding?.departureTimePicker?.setOnClickListener {
            showTimePickerDialog { time ->
                binding?.departureTimePicker?.setText(time)
            }
        }
    }

    private fun setupOwnerSwitch() {
        binding?.ownerSwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.getTechnicianInfo().observe(this) { technician ->
                    binding?.firstNameInput?.setText(technician.firstName)
                    binding?.lastNameInput?.setText(technician.lastName)
                }
                binding?.technicianNameGroup?.isEnabled = false
            } else {
                binding?.firstNameInput?.text?.clear()
                binding?.lastNameInput?.text?.clear()
                binding?.technicianNameGroup?.isEnabled = true
            }
        }
    }

    private fun populateFields(entry: TimeEntry) {
        binding?.apply {
            ownerSwitch.isChecked = entry.isOwner
            firstNameInput.setText(entry.technicianFirstName)
            lastNameInput.setText(entry.technicianLastName)
            arrivalTimePicker.setText(formatTime(entry.arrivalTime))
            departureTimePicker.setText(formatTime(entry.departureTime))
            interventionDurationInput.setText(entry.interventionDuration.toString())
            travelDurationInput.setText(entry.travelDuration.toString())
        }
    }

    private fun showTimePickerDialog(onTimeSet: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            requireContext(),
            { _, hour, minute ->
                onTimeSet(String.format("%02d:%02d", hour, minute))
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun saveTimeEntry() {
        val firstName = binding?.firstNameInput?.text.toString()
        val lastName = binding?.lastNameInput?.text.toString()
        val isOwner = binding?.ownerSwitch?.isChecked ?: false
        val arrivalTime = binding?.arrivalTimePicker?.text.toString()
        val departureTime = binding?.departureTimePicker?.text.toString()
        val interventionDuration = binding?.interventionDurationInput?.text.toString().toIntOrNull() ?: 0
        val travelDuration = binding?.travelDurationInput?.text.toString().toIntOrNull() ?: 0

        val entry = TimeEntry(
            id = timeEntry?.id ?: 0,
            reportId = reportId,
            technicianFirstName = firstName,
            technicianLastName = lastName,
            isOwner = isOwner,
            arrivalTime = parseTime(arrivalTime),
            departureTime = parseTime(departureTime),
            interventionDuration = interventionDuration,
            travelDuration = travelDuration
        )

        if (timeEntry == null) {
            viewModel.addTimeEntry(entry)
        } else {
            viewModel.updateTimeEntry(entry)
        }
    }

    private fun parseTime(timeString: String): Date {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        return format.parse(timeString) ?: Date()
    }

    private fun formatTime(date: Date): String {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        return format.format(date)
    }
}