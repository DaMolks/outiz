package com.example.outiz.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.databinding.FragmentTimeTrackingBinding
import com.example.outiz.models.TimeEntry
import com.example.outiz.ui.adapter.TimeEntryAdapter
import com.example.outiz.ui.dialog.AddTimeEntryDialog
import com.example.outiz.ui.viewmodel.ReportViewModel

class TimeTrackingFragment : Fragment() {
    private var _binding: FragmentTimeTrackingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ReportViewModel
    private lateinit var adapter: TimeEntryAdapter
    private var reportId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ReportViewModel::class.java]
        reportId = arguments?.getString(REPORT_ID_KEY) ?: ""
    }

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
        setupAddButton()
        observeTimeEntries()
    }

    private fun setupRecyclerView() {
        adapter = TimeEntryAdapter(
            onEditClick = { timeEntry -> showEditDialog(timeEntry) },
            onDeleteClick = { timeEntry -> deleteTimeEntry(timeEntry) }
        )

        binding.timeEntriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@TimeTrackingFragment.adapter
        }
    }

    private fun setupAddButton() {
        binding.addTimeEntryButton.setOnClickListener {
            AddTimeEntryDialog().apply {
                arguments = bundleOf(REPORT_ID_KEY to reportId)
            }.show(parentFragmentManager, "add_time_entry")
        }
    }

    private fun observeTimeEntries() {
        viewModel.loadTimeEntries(reportId)
        viewModel.timeEntries.observe(viewLifecycleOwner) { entries ->
            adapter.updateEntries(entries)
            binding.emptyStateText.visibility = if (entries.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun showEditDialog(timeEntry: TimeEntry) {
        AddTimeEntryDialog().apply {
            arguments = bundleOf(
                REPORT_ID_KEY to reportId,
                TIME_ENTRY_KEY to timeEntry
            )
        }.show(parentFragmentManager, "edit_time_entry")
    }

    private fun deleteTimeEntry(timeEntry: TimeEntry) {
        viewModel.deleteTimeEntry(timeEntry)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REPORT_ID_KEY = "reportId"
        private const val TIME_ENTRY_KEY = "timeEntry"

        fun newInstance(reportId: String) = TimeTrackingFragment().apply {
            arguments = bundleOf(REPORT_ID_KEY to reportId)
        }
    }
}