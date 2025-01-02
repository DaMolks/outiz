package com.example.outiz.ui.reports.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.databinding.FragmentReportTimeBinding
import com.example.outiz.models.TimeEntry
import com.example.outiz.ui.dialog.AddTimeEntryDialog
import com.example.outiz.ui.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class ReportTimeFragment : Fragment() {
    private var _binding: FragmentReportTimeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by viewModels({ requireParentFragment() })
    private lateinit var timeEntryAdapter: TimeEntryAdapter

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
        observeViewModel()
    }

    private fun setupRecyclerView() {
        timeEntryAdapter = TimeEntryAdapter { timeEntry ->
            viewModel.removeTimeEntry(timeEntry)
        }

        binding.rvTimeEntries.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = timeEntryAdapter
        }
    }

    private fun setupListeners() {
        binding.fabAddTime.setOnClickListener {
            showAddTimeEntryDialog()
        }
    }

    private fun observeViewModel() {
        viewModel.timeEntries.observe(viewLifecycleOwner) { entries ->
            timeEntryAdapter.submitList(entries)
            updateEmptyState(entries)
        }
    }

    private fun updateEmptyState(entries: List<TimeEntry>) {
        binding.tvEmptyList.isVisible = entries.isEmpty()
        binding.rvTimeEntries.isVisible = entries.isNotEmpty()
    }

    private fun showAddTimeEntryDialog() {
        AddTimeEntryDialog(requireContext()) { description, duration ->
            val timeEntry = TimeEntry(
                id = 0,
                reportId = viewModel.currentReport.value?.id ?: return@AddTimeEntryDialog,
                startTime = LocalDateTime.now(),
                duration = duration,
                description = description
            )
            viewModel.addTimeEntry(timeEntry)
        }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(reportId: Long) = ReportTimeFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_REPORT_ID, reportId)
            }
        }
        
        private const val ARG_REPORT_ID = "report_id"
    }
}
