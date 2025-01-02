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
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class ReportInfoFragment : Fragment() {
    private var _binding: FragmentReportInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditReportViewModel by viewModels()
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

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

        viewModel.callDate.observe(viewLifecycleOwner) { callDate ->
            binding.callDateInput.setText(dateFormatter.format(callDate))
        }

        viewModel.caller.observe(viewLifecycleOwner) { caller ->
            binding.callerInput.setText(caller)
        }

        viewModel.callReason.observe(viewLifecycleOwner) { reason ->
            binding.callReasonInput.setText(reason)
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

        binding.callerInput.setOnFocusChangeListener { _, _ ->
            viewModel.updateCaller(binding.callerInput.text.toString())
        }

        binding.callReasonInput.setOnFocusChangeListener { _, _ ->
            viewModel.updateCallReason(binding.callReasonInput.text.toString())
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
}