package com.example.outiz.ui.reports.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.databinding.FragmentReportTimeBinding
import com.example.outiz.models.TimeEntry

class ReportTimeFragment : Fragment() {
    private var _binding: FragmentReportTimeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TimeEntriesAdapter
    private var reportId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reportId = arguments?.getString(ARG_REPORT_ID)
    }

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
        setupAddButton()
    }

    private fun setupRecyclerView() {
        adapter = TimeEntriesAdapter(
            onEditClick = { timeEntry -> showEditDialog(timeEntry) },
            onDeleteClick = { timeEntry -> showDeleteDialog(timeEntry) }
        )

        binding.timeEntriesList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ReportTimeFragment.adapter
        }
    }

    private fun setupAddButton() {
        binding.addTimeEntryButton.setOnClickListener {
            // Récupérer l'ID du technicien depuis les préférences
            val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val technicianId = prefs.getString("technician_id", null)
            if (technicianId != null && reportId != null) {
                showAddDialog()
            }
        }
    }

    private fun showAddDialog() {
        // TODO: Implémenter l'ajout d'une entrée de temps
    }

    private fun showEditDialog(timeEntry: TimeEntry) {
        // TODO: Implémenter la modification d'une entrée de temps
    }

    private fun showDeleteDialog(timeEntry: TimeEntry) {
        // TODO: Implémenter la suppression d'une entrée de temps
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_REPORT_ID = "reportId"

        fun newInstance(reportId: String) = ReportTimeFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_REPORT_ID, reportId)
            }
        }
    }
}