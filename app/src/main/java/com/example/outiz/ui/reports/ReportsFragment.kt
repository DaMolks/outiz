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
    private lateinit var viewModel: ReportViewModel
    private lateinit var adapter: ReportsAdapter

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
        adapter = ReportsAdapter(
            onEditClick = { report -> navigateToEditReport(report.id) },
            onDetailsClick = { report -> navigateToReportDetails(report.id) }
        )
        binding.reportsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.reportsRecyclerView.adapter = adapter
    }

    private fun navigateToEditReport(reportId: String?) {
        val args = Bundle().apply { putString("reportId", reportId) }
        findNavController().navigate(R.id.editReportFragment, args)
    }

    private fun navigateToReportDetails(reportId: String?) {
        val args = Bundle().apply { putString("reportId", reportId) }
        findNavController().navigate(R.id.reportDetailsFragment, args)
    }

    private fun setupAddReportButton() {
        binding.addReportButton.setOnClickListener {
            findNavController().navigate(R.id.editReportFragment)
        }
    }

    private fun observeReports() {
        viewModel.loadReports()
        viewModel.reports.observe(viewLifecycleOwner) { reports ->
            adapter.submitList(reports)
            binding.emptyStateText.visibility = if (reports.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}