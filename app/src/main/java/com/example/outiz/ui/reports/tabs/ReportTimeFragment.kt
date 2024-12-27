package com.example.outiz.ui.reports.tabs

import android.app.Dialog
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
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDateTime

class ReportTimeFragment : Fragment() {
    private var _binding: FragmentReportTimeBinding? = null
    private val binding get() = _binding!!
    private lateinit var timeEntryAdapter: TimeEntryAdapter
    private val viewModel: ReportViewModel by viewModels(ownerProducer = { requireParentFragment() })

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
        timeEntryAdapter = TimeEntryAdapter()
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
        val descriptionEdit = dialog.findViewById<EditText>(R.id.editTextDescription)
        val taskTypeSpinner = dialog.findViewById<Spinner>(R.id.spinnerTaskType)
        val btnSave = dialog.findViewById<Button>(R.id.buttonSaveTimeEntry)

        // Populate task type spinner
        val taskTypes = arrayOf("Installation", "Maintenance", "Diagnostic", "Autre")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, taskTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        taskTypeSpinner.adapter = adapter

        btnSave.setOnClickListener {
            val startTime = LocalDateTime.parse(startTimeEdit.text.toString())
            val endTime = LocalDateTime.parse(endTimeEdit.text.toString())
            val description = descriptionEdit.text.toString()
            val taskType = taskTypeSpinner.selectedItem.toString()

            val timeEntry = TimeEntry(
                reportId = viewModel.currentReport.value?.id ?: "",
                startTime = startTime,
                endTime = endTime,
                description = description,
                taskType = taskType
            )

            viewModel.addTimeEntry(timeEntry)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}