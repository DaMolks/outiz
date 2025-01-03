package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
                binding.apply {
                    tvReportSiteName.text = report?.siteName ?: ""
                    tvReportDescription.text = report?.description ?: ""
                    tvReportDate.text = report?.date?.toString() ?: ""
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnEdit.setOnClickListener {
            viewModel.report.value?.id?.let { id ->
                findNavController().navigate(ReportDetailsFragmentDirections.actionReportDetailsToEditReport(id))
            }
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