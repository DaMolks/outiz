package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.R
import com.example.outiz.databinding.FragmentReportsBinding
import com.example.outiz.ui.adapter.ReportsAdapter
import com.example.outiz.ui.base.BaseFragment
import com.example.outiz.ui.viewmodel.ReportViewModel
import com.example.outiz.utils.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportsFragment : BaseFragment(R.layout.fragment_reports) {
    private lateinit var binding: FragmentReportsBinding
    private val viewModel: ReportViewModel by viewModels()
    private lateinit var adapter: ReportsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReportsBinding.bind(view)
        setupRecyclerView()
        setupSwipeRefresh()
        observeReports()
        setupListeners()
    }

    override fun getViewModel() = viewModel

    override fun handleLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        adapter = ReportsAdapter(onReportClick = { report ->
            navigate(ReportsFragmentDirections.actionReportsFragmentToReportDetailsFragment(report.id))
        })
        binding.reportsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ReportsFragment.adapter
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshReports()
        }
    }

    private fun observeReports() {
        viewModel.reportsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    adapter.submitList(state.data)
                    binding.emptyView.visibility = if (state.data.isEmpty()) View.VISIBLE else View.GONE
                    binding.swipeRefresh.isRefreshing = false
                }
                is State.Error -> {
                    errorHandler.handleError(state.message) {
                        viewModel.refreshReports()
                    }
                    binding.swipeRefresh.isRefreshing = false
                }
                is State.Loading -> {
                    // Loading handled by base class
                }
            }
        }

        viewModel.filterState.observe(viewLifecycleOwner) { filter ->
            when (filter) {
                ReportFilter.ALL -> binding.filterChipGroup.check(R.id.allChip)
                ReportFilter.WEEK -> binding.filterChipGroup.check(R.id.weekChip)
                ReportFilter.MONTH -> binding.filterChipGroup.check(R.id.monthChip)
            }
        }
    }

    private fun setupListeners() {
        binding.filterChipGroup.setOnCheckedStateChangeListener { group, _ ->
            when (group.checkedChipId) {
                R.id.allChip -> viewModel.setFilter(ReportFilter.ALL)
                R.id.weekChip -> viewModel.setFilter(ReportFilter.WEEK)
                R.id.monthChip -> viewModel.setFilter(ReportFilter.MONTH)
            }
        }

        binding.addReportFab.setOnClickListener {
            navigate(ReportsFragmentDirections.actionReportsFragmentToEditReportFragment())
        }
    }
}