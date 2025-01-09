package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.outiz.databinding.FragmentReportDetailsBinding
import com.example.outiz.ui.base.BaseFragment
import com.example.outiz.utils.formatDate
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ReportDetailsFragment : BaseFragment<FragmentReportDetailsBinding>(
    FragmentReportDetailsBinding::inflate
) {

    private val viewModel: ReportViewModel by viewModels()
    private val args: ReportDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        observeReport()
        setupListeners()
        loadReport()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeReport() {
        viewModel.currentReport.observe(viewLifecycleOwner) { report ->
            report?.let {
                with(binding) {
                    tvReportSiteName.text = it.siteName
                    tvReportDescription.text = it.description
                    tvReportDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        .format(it.date)
                }
            }
        }
    }

    private fun setupListeners() {
        binding.fabEdit.setOnClickListener {
            viewModel.currentReport.value?.let { report ->
                findNavController().navigate(
                    ReportDetailsFragmentDirections.actionReportDetailsToEdit(report.id)
                )
            }
        }
    }

    private fun loadReport() {
        viewModel.loadReport(args.reportId)
    }
}