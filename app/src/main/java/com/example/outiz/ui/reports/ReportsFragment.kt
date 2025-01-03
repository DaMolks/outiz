package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.databinding.FragmentReportsBinding

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
        observeReports()
        setupFabAddReport()
    }

    private fun setupRecyclerView() {
        reportsAdapter = ReportsAdapter { report ->
            // Navigation to report details
            val action = ReportsFragmentDirections.actionReportsToReportDetails(report.id)
            findNavController().navigate(action)
        }

        binding.rvReports.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reportsAdapter
        }
    }

    private fun observeReports() {
        viewModel.reports.observe(viewLifecycleOwner) { reports ->
            reportsAdapter.submitList(reports)
        }
    }

    private fun setupFabAddReport() {
        binding.fabAddReport.setOnClickListener {
            // Navigation to add report
            val action = ReportsFragmentDirections.actionReportsToAddReport()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}