package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.outiz.R
import com.example.outiz.databinding.FragmentReportDetailsBinding

class ReportDetailsFragment : Fragment() {

    private var _binding: FragmentReportDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ajouter un bouton pour accéder à la saisie du temps
        binding.addTimeEntryButton.setOnClickListener {
            findNavController().navigate(
                ReportDetailsFragmentDirections.actionReportDetailsFragmentToTimeEntryFragment(
                    reportId = "test_report_id" // À remplacer par l'ID réel du rapport
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}