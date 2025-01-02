package com.example.outiz.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.outiz.data.OutizDatabase
import com.example.outiz.databinding.FragmentTimeTrackingBinding
import com.example.outiz.models.TimeEntry
import com.example.outiz.ui.adapter.TimeEntryAdapter
import com.example.outiz.ui.dialog.AddTimeEntryDialog
import com.example.outiz.ui.reports.ReportViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId

class TimeTrackingFragment : Fragment() {
    private var _binding: FragmentTimeTrackingBinding? = null
    private val binding get() = _binding!!
    
    private val reportViewModel: ReportViewModel by viewModels()
    private val timeEntryDao by lazy { 
        OutizDatabase.getDatabase(requireContext()).timeEntryDao() 
    }
    
    private lateinit var timeEntryAdapter: TimeEntryAdapter
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTimeTrackingBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Récupérer l'ID du rapport depuis les arguments
        val reportId = arguments?.getLong(ARG_REPORT_ID) ?: return
        
        setupRecyclerView()
        setupObservers(reportId)
        setupListeners(reportId)
    }
    
    private fun setupRecyclerView() {
        timeEntryAdapter = TimeEntryAdapter { timeEntry ->
            lifecycleScope.launch {
                timeEntryDao.delete(timeEntry)
            }
        }
        binding.rvTimeEntries.adapter = timeEntryAdapter
    }
    
    private fun setupObservers(reportId: Long) {
        reportViewModel.getTimeEntriesForReport(reportId).observe(viewLifecycleOwner) { timeEntries ->
            timeEntryAdapter.submitList(timeEntries)
        }
    }
    
    private fun setupListeners(reportId: Long) {
        binding.fabAddTime.setOnClickListener {
            showAddTimeEntryDialog(reportId)
        }
    }
    
    private fun showAddTimeEntryDialog(reportId: Long) {
        AddTimeEntryDialog(requireContext()) { description, duration ->
            val timeEntry = TimeEntry(
                id = 0, // Room will generate the ID
                reportId = reportId,
                startTime = LocalDateTime.now(),
                duration = duration,
                description = description
            )
            
            lifecycleScope.launch {
                timeEntryDao.insert(timeEntry)
            }
        }.show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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