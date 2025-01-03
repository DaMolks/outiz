package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.outiz.databinding.FragmentEditReportBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditReportFragment : Fragment() {
    private var _binding: FragmentEditReportBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditReportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.hasTimeTracking.observe(viewLifecycleOwner) { hasTimeTracking ->
            binding.switchTimeTracking.isChecked = hasTimeTracking
        }

        viewModel.hasPhotos.observe(viewLifecycleOwner) { hasPhotos ->
            binding.switchPhotos.isChecked = hasPhotos
        }

        viewModel.reportSaved.observe(viewLifecycleOwner) { isSaved ->
            if (isSaved) {
                findNavController().navigateUp()
            }
        }
    }

    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            viewModel.saveReport(
                binding.etSiteName.text.toString(),
                binding.etDescription.text.toString()
            )
        }

        binding.switchTimeTracking.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateHasTimeTracking(isChecked)
        }

        binding.switchPhotos.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateHasPhotos(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}