package com.example.outiz.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.outiz.data.dao.TimeEntryDao
import com.example.outiz.databinding.FragmentTimeTrackingBinding
import com.example.outiz.ui.reports.ReportViewModel
import com.example.outiz.utils.TimeEntryAdapter
import kotlinx.coroutines.launch

class TimeTrackingFragment : Fragment() {
    private lateinit var binding: FragmentTimeTrackingBinding
    private val reportViewModel: ReportViewModel by viewModels()
    private val timeEntryDao: TimeEntryDao by lazy { /* Inject or get from database */ }
    private val timeEntryAdapter = TimeEntryAdapter { timeEntry ->
        // Handle time entry deletion
        lifecycleScope.launch {
            timeEntryDao.delete(timeEntry)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTimeTrackingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTimeEntries.adapter = timeEntryAdapter

        val reportId = arguments?.getLong(ARG_REPORT_ID) ?: return

        reportViewModel.getTimeEntriesForReport(reportId).observe(viewLifecycleOwner) { timeEntries ->
            timeEntryAdapter.submitList(timeEntries)
        }
    }

    companion object {
        private const val ARG_REPORT_ID = "report_id"

        fun newInstance(reportId: Long): TimeTrackingFragment {
            val fragment = TimeTrackingFragment()
            val args = Bundle()
            args.putLong(ARG_REPORT_ID, reportId)
            fragment.arguments = args
            return fragment
        }
    }
}