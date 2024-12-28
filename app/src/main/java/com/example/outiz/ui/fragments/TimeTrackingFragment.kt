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
import com.example.outiz.ui.adapter.TimeEntryAdapter
import com.example.outiz.ui.reports.ReportViewModel
import kotlinx.coroutines.launch

class TimeTrackingFragment : Fragment() {
    private lateinit var binding: FragmentTimeTrackingBinding
    private val reportViewModel: ReportViewModel by viewModels()
    private val timeEntryDao: TimeEntryDao by lazy { /* TODO: Inject via Dependency Injection */ }

    private lateinit var timeEntryAdapter: TimeEntryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTimeTrackingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupérer l'ID du rapport depuis les arguments
        val reportId = arguments?.getLong(ARG_REPORT_ID) ?: return

        // Initialiser l'adaptateur avec une logique de suppression
        timeEntryAdapter = TimeEntryAdapter { timeEntry ->
            lifecycleScope.launch {
                timeEntryDao.delete(timeEntry)
            }
        }

        // Configurer le RecyclerView
        binding.rvTimeEntries.adapter = timeEntryAdapter

        // Observer les entrées de temps pour ce rapport
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