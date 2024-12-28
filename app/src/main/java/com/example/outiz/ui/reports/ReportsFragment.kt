package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.outiz.R
import com.example.outiz.databinding.FragmentReportsBinding
import com.example.outiz.ui.adapter.ReportsAdapter

class ReportsFragment : Fragment() {
    private lateinit var binding: FragmentReportsBinding
    private val viewModel: ReportsViewModel by viewModels()
    private lateinit var reportsAdapter: ReportsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialiser l'adaptateur
        reportsAdapter = ReportsAdapter { reportWithDetails ->
            findNavController().navigate(
                ReportsFragmentDirections.actionReportsFragmentToReportDetailsFragment(
                    reportId = reportWithDetails.report.id
                )
            )
        }

        // Configurer le RecyclerView
        binding.rvReports.adapter = reportsAdapter

        // Observer les rapports
        viewModel.reportsWithDetails.observe(viewLifecycleOwner) { reports ->
            reportsAdapter.submitList(reports)
        }

        // Bouton pour ajouter un nouveau rapport
        binding.fabAddReport.setOnClickListener {
            findNavController().navigate(R.id.action_reportsFragment_to_editReportFragment)
        }
    }
}