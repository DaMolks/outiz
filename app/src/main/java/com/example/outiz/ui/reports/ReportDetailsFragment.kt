package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.outiz.databinding.FragmentReportDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportDetailsFragment : Fragment() {
    private var _binding: FragmentReportDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportDetailsViewModel by viewModels()

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

        val reportId = arguments?.getLong(ARG_REPORT_ID) ?: -1L
        setupObservers(reportId)
        setupListeners()
    }

    private fun setupObservers(reportId: Long) {
        if (reportId != -1L) {
            viewModel.loadReport(reportId)
            viewModel.report.observe(viewLifecycleOwner) { report ->
                binding.tvSiteName.text = report?.siteName
                binding.tvDescription.text = report?.description
            }
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            // Navigation back logic
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_REPORT_ID = "report_id"

        fun newInstance(reportId: Long) = ReportDetailsFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_REPORT_ID, reportId)
            }
        }
    }
}