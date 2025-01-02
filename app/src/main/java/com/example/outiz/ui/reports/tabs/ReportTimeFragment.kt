package com.example.outiz.ui.reports.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.databinding.FragmentReportTimeBinding
import com.example.outiz.ui.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportTimeFragment : Fragment() {
    private var _binding: FragmentReportTimeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by viewModels()
    private lateinit var timeEntriesAdapter: TimeEntriesAdapter

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
        setupObservers()

        val reportId = arguments?.getLong(ARG_REPORT_ID) ?: -1L
        if (reportId != -1L) {
            viewModel.loadReport(reportId)
        }
    }

    private fun setupRecyclerView() {
        timeEntriesAdapter = TimeEntriesAdapter()
        binding.rvTimeEntries.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = timeEntriesAdapter
        }
    }

    private fun setupObservers() {
        viewModel.timeEntries.observe(viewLifecycleOwner) { entries ->
            timeEntriesAdapter.submitList(entries)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_REPORT_ID = "report_id"

        fun newInstance(reportId: Long) = ReportTimeFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_REPORT_ID, reportId)
            }
        }
    }
}