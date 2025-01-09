package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.outiz.databinding.FragmentEditReportBinding
import com.example.outiz.ui.base.BaseFragment
import com.example.outiz.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EditReportFragment : BaseFragment<FragmentEditReportBinding>(
    FragmentEditReportBinding::inflate
) {

    private val viewModel: EditReportViewModel by viewModels()
    private val args: EditReportFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeViewModel()
        loadReport()
    }

    private fun setupViews() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSave.setOnClickListener {
            viewModel.saveReport(
                siteName = binding.etSiteName.text.toString(),
                description = binding.etDescription.text.toString(),
                hasTimeTracking = binding.switchTimeTracking.isChecked,
                hasPhotos = binding.switchPhotos.isChecked
            )
        }
    }

    private fun observeViewModel() {
        viewModel.saveCompleted.observe(viewLifecycleOwner) { completed ->
            if (completed) {
                findNavController().navigateUp()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding.root.showSnackbar(it)
            }
        }
    }

    private fun loadReport() {
        if (args.reportId != -1L) {
            viewModel.loadReport(args.reportId)
        }
    }
}