package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.R
import com.example.outiz.databinding.FragmentReportsBinding
import com.example.outiz.models.ReportWithDetails
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ReportsFragment : Fragment() {

    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReportsViewModel by viewModels()

    private lateinit var reportsAdapter: ReportsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupChipGroup()
        setupListeners()
    }

    private fun setupRecyclerView() {
        reportsAdapter = ReportsAdapter(
            onReportClick = { report -> navigateToReportDetails(report) },
            onReportEdit = { report -> navigateToEditReport(report) },
            onReportDelete = { report -> showDeleteConfirmation(report) }
        )

        binding.reportsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reportsAdapter
        }
    }

    private fun setupObservers() {
        viewModel.reports.observe(viewLifecycleOwner) { reports ->
            reportsAdapter.submitList(reports)
            binding.emptyView.visibility = if (reports.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }

    private fun setupChipGroup() {
        binding.filterChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            when ((group.findViewById<Chip>(checkedIds[0])).id) {
                R.id.allChip -> viewModel.loadReports()
                R.id.weekChip -> viewModel.loadReportsForWeek()
                R.id.monthChip -> viewModel.loadReportsForMonth()
            }
        }
    }

    private fun setupListeners() {
        binding.addReportFab.setOnClickListener {
            navigateToCreateReport()
        }
    }

    private fun navigateToReportDetails(report: ReportWithDetails) {
        val action = ReportsFragmentDirections.actionReportsFragmentToReportDetailsFragment(report.report.id)
        findNavController().navigate(action)
    }

    private fun navigateToCreateReport() {
        val action = ReportsFragmentDirections.actionReportsFragmentToEditReportFragment(null)
        findNavController().navigate(action)
    }

    private fun navigateToEditReport(report: ReportWithDetails) {
        val action = ReportsFragmentDirections.actionReportsFragmentToEditReportFragment(report.report.id)
        findNavController().navigate(action)
    }

    private fun showDeleteConfirmation(report: ReportWithDetails) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_report)
            .setMessage(getString(R.string.delete_report_confirmation))
            .setPositiveButton(R.string.delete) { _, _ -> viewModel.deleteReport(report) }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}