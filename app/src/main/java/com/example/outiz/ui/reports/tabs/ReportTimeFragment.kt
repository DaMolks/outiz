package com.example.outiz.ui.reports.tabs

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.R
import com.example.outiz.databinding.FragmentReportTimeBinding
import com.example.outiz.models.TimeEntry
import com.example.outiz.ui.reports.ReportViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date

class ReportTimeFragment : Fragment() {
    private var _binding: FragmentReportTimeBinding? = null
    private val binding get() = _binding!!
    private lateinit var timeEntryAdapter: TimeEntriesAdapter
    private val viewModel: ReportViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private var startTime: LocalTime? = null
    private var endTime: LocalTime? = null
    private val technicians = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
        observeTimeEntries()
    }

    private fun setupRecyclerView() {
        timeEntryAdapter = TimeEntriesAdapter(
            onEditClick = { /* TODO: Implémenter l'édition */ },
            onDeleteClick = { viewModel.removeTimeEntry(it) }
        )
        binding.recyclerViewTime.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = timeEntryAdapter
        }
    }

    private fun setupListeners() {
        binding.addTimeButton.setOnClickListener {
            showAddTimeEntryDialog()
        }
    }

    private fun observeTimeEntries() {
        viewModel.timeEntries.observe(viewLifecycleOwner) { entries ->
            timeEntryAdapter.submitList(entries)
        }
    }

    private fun showAddTimeEntryDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_add_time_entry)

        val startTimeEdit = dialog.findViewById<TextInputEditText>(R.id.editTextStartTime)
        val endTimeEdit = dialog.findViewById<TextInputEditText>(R.id.editTextEndTime)
        val durationInput = dialog.findViewById<TextInputEditText>(R.id.durationInput)
        val descriptionEdit = dialog.findViewById<EditText>(R.id.editTextDescription)
        val taskTypeSpinner = dialog.findViewById<Spinner>(R.id.spinnerTaskType)
        val technicianInput = dialog.findViewById<TextInputEditText>(R.id.technicianInput)
        val addTechnicianButton = dialog.findViewById<Button>(R.id.addTechnicianButton)
        val techniciansChipGroup = dialog.findViewById<com.google.android.material.chip.ChipGroup>(R.id.techniciansChipGroup)
        val btnSave = dialog.findViewById<Button>(R.id.buttonSaveTimeEntry)

        // Populate task type spinner
        val taskTypes = arrayOf("Installation", "Maintenance", "Diagnostic", "Autre")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, taskTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        taskTypeSpinner.adapter = adapter

        startTimeEdit.setOnClickListener {
            showTimePickerDialog(true) { time ->
                startTime = time
                startTimeEdit.setText(time.format(timeFormatter))
                updateDuration(durationInput)
            }
        }

        endTimeEdit.setOnClickListener {
            showTimePickerDialog(false) { time ->
                endTime = time
                endTimeEdit.setText(time.format(timeFormatter))
                updateDuration(durationInput)
            }
        }

        addTechnicianButton.setOnClickListener {
            val technicianName = technicianInput.text?.toString()?.trim()
            if (!technicianName.isNullOrBlank()) {
                addTechnicianChip(technicianName, techniciansChipGroup)
                technicianInput.text?.clear()
            }
        }

        btnSave.setOnClickListener {
            val description = descriptionEdit.text.toString()
            val taskType = taskTypeSpinner.selectedItem.toString()
            val now = LocalDateTime.now()

            // Convertir les LocalTime en LocalDateTime
            val startDateTime = startTime?.let { LocalDateTime.of(now.toLocalDate(), it) } ?: now
            val endDateTime = endTime?.let { LocalDateTime.of(now.toLocalDate(), it) } ?: now

            val timeEntry = TimeEntry(
                reportId = viewModel.currentReport.value?.id ?: "",
                date = Date(),
                duration = calculateDuration(),
                startTime = startDateTime,
                endTime = endDateTime,
                description = description,
                taskType = taskType,
                technicians = technicians.toList()
            )

            viewModel.addTimeEntry(timeEntry)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showTimePickerDialog(isStartTime: Boolean, callback: (LocalTime) -> Unit) {
        val currentTime = LocalTime.now()
        TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                val time = LocalTime.of(hourOfDay, minute)
                callback(time)
            },
            currentTime.hour,
            currentTime.minute,
            true
        ).show()
    }

    private fun updateDuration(durationInput: TextInputEditText) {
        val duration = calculateDuration()
        val hours = duration / 60
        val minutes = duration % 60
        durationInput.setText(String.format("%02dh%02d", hours, minutes))
    }

    private fun calculateDuration(): Int {
        if (startTime == null || endTime == null) return 0
        
        var minutes = (endTime!!.hour * 60 + endTime!!.minute) - 
                      (startTime!!.hour * 60 + startTime!!.minute)
        
        if (minutes < 0) {
            minutes += 24 * 60 // Ajouter 24h si l'heure de fin est le jour suivant
        }
        
        return minutes
    }

    private fun addTechnicianChip(technicianName: String, chipGroup: com.google.android.material.chip.ChipGroup) {
        if (technicianName in technicians) return

        technicians.add(technicianName)
        val chip = Chip(requireContext()).apply {
            text = technicianName
            isCloseIconVisible = true
            setOnCloseIconClickListener {
                chipGroup.removeView(this)
                technicians.remove(technicianName)
            }
        }
        chipGroup.addView(chip)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}