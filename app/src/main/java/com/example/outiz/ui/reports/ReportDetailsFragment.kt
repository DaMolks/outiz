package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.outiz.databinding.FragmentReportDetailsBinding

class ReportDetailsFragment : Fragment() {
    private var _binding: FragmentReportDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditReportViewModel by viewModels()

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

        // Récupérer l'ID du rapport depuis les arguments
        val reportId = arguments?.getString("reportId") ?: return

        // Charger les détails du rapport
        viewModel.loadReport(reportId)

        // Observer les détails du rapport
        viewModel.report.observe(viewLifecycleOwner) { reportWithDetails ->
            reportWithDetails?.let { report ->
                // Mettre à jour l'interface utilisateur avec les détails du rapport
                binding.textViewSiteName.text = report.site.name
                binding.textViewCallDate.text = report.report.callDate.toString()
                binding.textViewCallReason.text = report.report.callReason
                binding.textViewCaller.text = report.report.caller

                // Afficher les entrées de temps
                if (report.report.isTimeTrackingEnabled) {
                    val timeEntries = report.timeEntries
                    // TODO: Implémenter l'affichage des entrées de temps
                }

                // Afficher les photos
                if (report.report.isPhotosEnabled) {
                    val photos = report.report.photosPaths ?: emptyList()
                    // TODO: Implémenter l'affichage des photos
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}