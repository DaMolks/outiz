package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.R
import com.example.outiz.databinding.FragmentReportsBinding
import com.example.outiz.ui.adapter.ReportsAdapter

class ReportsFragment : Fragment() {
    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!
    private lateinit var reportsAdapter: ReportsAdapter
    private lateinit var viewModel: ReportViewModel

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

        viewModel = ViewModelProvider(this)[ReportViewModel::class.java]

        setupRecyclerView()
        setupAddReportButton()
        observeReports()
    }

    private fun setupRecyclerView() {
        reportsAdapter = ReportsAdapter(
            onReportClick = { report -> navigateToReportDetails(report.id) },
            onReportEdit = { report -> navigateToEditReport(report.id) },
            onReportDelete = { report -> deleteReport(report) }
        )

        binding.reportsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reportsAdapter
        }
    }

    private fun navigateToReportDetails(reportId: String?) {
        reportId?.let {
            val args = Bundle().apply { putString("reportId", it) }
            findNavController().navigate(R.id.reportDetailsFragment, args)
        }
    }

    private fun navigateToEditReport(reportId: String?) {
        reportId?.let {
            val args = Bundle().apply { putString("reportId", it) }
            findNavController().navigate(R.id.editReportFragment, args)
        }
    }

    private fun deleteReport(report: com.example.outiz.models.Report) {
        viewModel.deleteReport(report)
    }

    private fun setupAddReportButton() {
        binding.addReportButton.setOnClickListener {
            findNavController().navigate(R.id.editReportFragment)
        }
    }

    private fun observeReports() {
        viewModel.loadReports()
        viewModel.reports.observe(viewLifecycleOwner) { reports ->
            reportsAdapter.submitList(reports)
            binding.emptyStateText.visibility = if (reports.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}