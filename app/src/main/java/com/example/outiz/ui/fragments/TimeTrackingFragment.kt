package com.example.outiz.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.databinding.FragmentTimeTrackingBinding
import com.example.outiz.models.TimeEntry
import com.example.outiz.ui.reports.tabs.TimeEntriesAdapter
import com.example.outiz.ui.viewmodel.TimeTrackingViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class TimeTrackingFragment : Fragment() {
    private var _binding: FragmentTimeTrackingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TimeTrackingViewModel by viewModels()
    private lateinit var timeEntriesAdapter: TimeEntriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimeTrackingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        timeEntriesAdapter = TimeEntriesAdapter(
            onDeleteClick = { timeEntry ->
                viewModel.deleteTimeEntry(timeEntry)
            },
            onEditClick = { _ ->
                // TODO: Implement edit time entry
            }
        )
        binding.rvTimeEntries.layoutManager = LinearLayoutManager(context)
        binding.rvTimeEntries.adapter = timeEntriesAdapter
    }

    private fun setupObservers() {
        viewModel.timeEntries.observe(viewLifecycleOwner) { entries ->
            timeEntriesAdapter.submitList(entries)
        }
    }

    private fun setupListeners() {
        binding.fabAddTime.setOnClickListener {
            val newTimeEntry = TimeEntry(
                id = 0,
                reportId = 1, // TODO: Get dynamic report ID
                startTime = Date(), // Utiliser java.util.Date
                duration = 30, // Default 30 minutes
                description = "New Time Entry"
            )
            viewModel.addTimeEntry(newTimeEntry)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = TimeTrackingFragment()
    }
}