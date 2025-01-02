package com.example.outiz.ui.reports.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.outiz.databinding.FragmentReportInfoBinding
import com.example.outiz.ui.reports.EditReportViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportInfoFragment : Fragment() {
    private var _binding: FragmentReportInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditReportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reportId = arguments?.getLong(ARG_REPORT_ID) ?: -1L
        if (reportId != -1L) {
            viewModel.loadReport(reportId)
        }

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.siteName.observe(viewLifecycleOwner) { siteName ->
            binding.siteInput.setText(siteName)
        }

        viewModel.description.observe(viewLifecycleOwner) { description ->
            binding.descriptionInput.setText(description)
        }

        viewModel.hasTimeTracking.observe(viewLifecycleOwner) { hasTimeTracking ->
            binding.timeTrackingSwitch.isChecked = hasTimeTracking
        }

        viewModel.hasPhotos.observe(viewLifecycleOwner) { hasPhotos ->
            binding.photosSwitch.isChecked = hasPhotos
        }
    }

    private fun setupListeners() {
        binding.siteInput.setOnFocusChangeListener { _, _ ->
            viewModel.updateSiteName(binding.siteInput.text.toString())
        }

        binding.descriptionInput.setOnFocusChangeListener { _, _ ->
            viewModel.updateDescription(binding.descriptionInput.text.toString())
        }

        binding.timeTrackingSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateHasTimeTracking(isChecked)
        }

        binding.photosSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateHasPhotos(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_REPORT_ID = "report_id"

        fun newInstance(reportId: Long) = ReportInfoFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_REPORT_ID, reportId)
            }
        }
    }
}