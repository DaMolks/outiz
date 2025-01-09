package com.example.outiz.ui.reports.tabs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.databinding.FragmentTimeTrackingBinding
import com.example.outiz.models.TimeEntry
import com.example.outiz.ui.adapter.TimeEntryAdapter
import com.example.outiz.ui.base.BaseFragment
import com.example.outiz.ui.viewmodel.TimeTrackingViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

@AndroidEntryPoint
class TimeTrackingFragment : BaseFragment<FragmentTimeTrackingBinding>(
    FragmentTimeTrackingBinding::inflate
) {

    private val viewModel: TimeTrackingViewModel by viewModels()
    private lateinit var timeEntryAdapter: TimeEntryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        timeEntryAdapter = TimeEntryAdapter { timeEntry ->
            viewModel.removeTimeEntry(timeEntry)
        }
        
        binding.recyclerView.apply {
            adapter = timeEntryAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupListeners() {
        binding.fabAddTime.setOnClickListener {
            showTimeEntryDialog()
        }
    }

    private fun observeViewModel() {
        viewModel.timeEntries.observe(viewLifecycleOwner) { entries ->
            timeEntryAdapter.submitList(entries)
            updateEmptyState(entries.isEmpty())
        }
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun showTimeEntryDialog() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Sélectionner la date")
            .build()

        datePicker.addOnPositiveButtonClickListener { dateInMillis ->
            val selectedDate = LocalDate.ofInstant(
                java.time.Instant.ofEpochMilli(dateInMillis),
                ZoneId.systemDefault()
            )
            
            showTimePicker(selectedDate)
        }

        datePicker.show(parentFragmentManager, "date_picker")
    }

    private fun showTimePicker(date: LocalDate) {
        val now = LocalTime.now()
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(now.hour)
            .setMinute(now.minute)
            .build()

        timePicker.addOnPositiveButtonClickListener {
            val time = LocalTime.of(timePicker.hour, timePicker.minute)
            val dateTime = LocalDateTime.of(date, time)
            showDurationDialog(dateTime)
        }

        timePicker.show(parentFragmentManager, "time_picker")
    }

    private fun showDurationDialog(startTime: LocalDateTime) {
        // TODO: Implémenter le dialogue de durée
        // Pour l'instant, on utilise une durée fixe de 60 minutes
        addTimeEntry(startTime, 60, "Intervention standard")
    }

    private fun addTimeEntry(startTime: LocalDateTime, duration: Int, description: String) {
        viewModel.addTimeEntry(duration, description, startTime)
    }
}