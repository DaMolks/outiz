package com.example.outiz.ui.reports.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.databinding.FragmentReportTimeBinding
import com.example.outiz.models.TimeEntry
import com.example.outiz.ui.dialog.AddTimeEntryDialog
import com.example.outiz.ui.viewmodel.ReportViewModel

class ReportTimeFragment : Fragment() {
    private lateinit var binding: FragmentReportTimeBinding
    private lateinit var viewModel: ReportViewModel
    private lateinit var adapter: TimeEntriesAdapter
    private var reportId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reportId = arguments?.getString(ARG_REPORT_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ReportViewModel::class.java]

        setupRecyclerView()
        setupAddButton()
        observeTimeEntries()
    }

    private fun setupRecyclerView() {
        adapter = TimeEntriesAdapter(
            onEditClick = { timeEntry -> showEditDialog(timeEntry) },
            onDeleteClick = { timeEntry -> deleteTimeEntry(timeEntry) }
        )

        binding.timeEntriesList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@ReportTimeFragment.adapter
        }
    }

    private fun setupAddButton() {
        binding.addTimeEntryButton.setOnClickListener {
            val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val technicianId = prefs.getString("technician_id", null)
            if (technicianId != null && reportId != null) {
                showAddDialog()
            }
        }
    }

    private fun showAddDialog() {
        val dialog = AddTimeEntryDialog().apply {
            arguments = Bundle().apply { 
                putString("reportId", reportId) 
            }
        }
        dialog.show(parentFragmentManager, "add_time_entry")
    }

    private fun showEditDialog(timeEntry: TimeEntry) {
        val dialog = AddTimeEntryDialog().apply {
            arguments = Bundle().apply { 
                putString("reportId", reportId)
                putParcelable("timeEntry", timeEntry)
            }
        }
        dialog.show(parentFragmentManager, "edit_time_entry")
    }

    private fun deleteTimeEntry(timeEntry: TimeEntry) {
        viewModel.deleteTimeEntry(timeEntry)
    }

    private fun observeTimeEntries() {
        reportId?.let { viewModel.loadTimeEntries(it) }
        viewModel.timeEntries.observe(viewLifecycleOwner) { entries ->
            adapter.updateEntries(entries)
            binding.emptyStateText.visibility = if (entries.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    companion object {
        private const val ARG_REPORT_ID = "reportId"

        fun newInstance(reportId: String) = ReportTimeFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_REPORT_ID, reportId)
            }
        }
    }
}