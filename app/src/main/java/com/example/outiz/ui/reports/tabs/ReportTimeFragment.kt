package com.example.outiz.ui.reports.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.databinding.FragmentReportTimeBinding
import com.example.outiz.models.TimeEntry
import com.example.outiz.ui.reports.EditReportViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Date

class ReportTimeFragment : Fragment() {

    private var _binding: FragmentReportTimeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditReportViewModel by viewModels({ requireParentFragment() })

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
        setupListeners()
    }

    private fun setupRecyclerView() {
        timeEntriesAdapter = TimeEntriesAdapter { entry ->
            viewModel.removeTimeEntry(entry)
        }

        binding.timeEntriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = timeEntriesAdapter
        }
    }

    private fun setupObservers() {
        viewModel.timeEntries.observe(viewLifecycleOwner) { entries ->
            timeEntriesAdapter.submitList(entries)
            binding.emptyView.visibility = if (entries.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun setupListeners() {
        binding.addTimeButton.setOnClickListener {
            showAddTimeDialog()
        }
    }

    private fun showAddTimeDialog() {
        // TODO: Implémenter le dialog pour ajouter le temps
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val technicianId = prefs.getString("technician_id", null) ?: return

        viewModel.addTimeEntry(
            technicianId = technicianId,
            date = Date(),
            duration = 60 // 1 heure par défaut
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}