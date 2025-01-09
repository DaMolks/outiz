package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.R
import com.example.outiz.databinding.FragmentReportsBinding
import com.example.outiz.models.Report
import com.example.outiz.ui.adapter.ReportsAdapter
import com.example.outiz.ui.base.BaseFragment
import com.example.outiz.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportsFragment : BaseFragment<FragmentReportsBinding>(
    FragmentReportsBinding::inflate
) {

    private val viewModel: ReportsViewModel by viewModels()
    private lateinit var reportsAdapter: ReportsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupListeners()
        setupObservers()
    }

    private fun setupRecyclerView() {
        reportsAdapter = ReportsAdapter { report -> navigateToDetails(report) }
        binding.recyclerView.apply {
            adapter = reportsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshReports()
        }

        binding.fabAddReport.setOnClickListener {
            findNavController().navigate(R.id.action_reports_to_edit)
        }
    }

    private fun setupObservers() {
        viewModel.reports.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    reportsAdapter.submitList(resource.data)
                    updateEmptyState(resource.data.isEmpty())
                }
                is Resource.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    showError(resource.message ?: getString(R.string.error_loading_data))
                }
                is Resource.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }
            }
        }
    }

    private fun navigateToDetails(report: Report) {
        val action = ReportsFragmentDirections.actionReportsToDetails(report.id)
        findNavController().navigate(action)
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }
}