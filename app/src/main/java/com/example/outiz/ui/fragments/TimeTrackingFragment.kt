package com.example.outiz.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.databinding.FragmentTimeTrackingBinding
import com.example.outiz.ui.reports.tabs.TimeEntriesAdapter
import com.example.outiz.ui.viewmodel.TimeTrackingViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

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
            onEditClick = { timeEntry ->
                // Implement edit functionality
            }
        )
        binding.rvTimeEntries.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = timeEntriesAdapter
        }
    }

    private fun setupObservers() {
        viewModel.timeEntries.observe(viewLifecycleOwner) { entries ->
            timeEntriesAdapter.submitList(entries)
        }
    }

    private fun setupListeners() {
        binding.fabAddTimeEntry.setOnClickListener {
            // Add time entry logic
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}