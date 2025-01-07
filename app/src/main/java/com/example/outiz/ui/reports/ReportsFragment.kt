package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.outiz.R
import com.example.outiz.ui.base.NavigationFragment
import com.example.outiz.databinding.FragmentReportsBinding
import com.example.outiz.ui.adapter.ReportsAdapter
import com.example.outiz.ui.viewmodel.ReportViewModel
dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportsFragment : NavigationFragment(R.layout.fragment_reports) {
    private lateinit var binding: FragmentReportsBinding
    private val viewModel: ReportViewModel by viewModels()
    private lateinit var adapter: ReportsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReportsBinding.bind(view)
        setupRecyclerView()
        observeReports()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = ReportsAdapter { report ->
            val action = ReportsFragmentDirections.actionReportsFragmentToReportDetailsFragment(report.id)
            navigate(action)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun observeReports() {
        viewModel.allReports.observe(viewLifecycleOwner) { reports ->
            adapter.submitList(reports)
            binding.emptyView.visibility = if (reports.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun setupListeners() {
        binding.fabAddReport.setOnClickListener {
            val action = ReportsFragmentDirections.actionReportsFragmentToEditReportFragment(-1L)
            navigate(action)
        }
    }
}