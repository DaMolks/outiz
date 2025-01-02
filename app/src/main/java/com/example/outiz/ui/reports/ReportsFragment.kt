package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.databinding.FragmentReportsBinding
import com.example.outiz.ui.adapter.ReportsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        setupListeners()
    }

    private fun setupRecyclerView() {
        reportsAdapter = ReportsAdapter { reportId ->
            // Navigation to report details
        }
        binding.rvReports.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reportsAdapter
        }
    }

    private fun setupObservers() {
        viewModel.reports.observe(viewLifecycleOwner) { reports ->
            reportsAdapter.submitList(reports)
        }
    }

    private fun setupListeners() {
        binding.fabAddReport.setOnClickListener {
            // Navigation to add report
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}