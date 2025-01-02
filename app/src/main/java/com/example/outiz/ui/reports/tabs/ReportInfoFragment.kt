package com.example.outiz.ui.reports.tabs

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.outiz.databinding.FragmentReportInfoBinding
import com.example.outiz.ui.reports.EditReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ReportInfoFragment : Fragment() {
    private var _binding: FragmentReportInfoBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: EditReportViewModel by viewModels({ requireParentFragment() })
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    
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
        
        setupInputListeners()
        observeViewModel()
    }
    
    private fun setupInputListeners() {
        binding.siteInput.doAfterTextChanged { text ->
            viewModel.updateSiteName(text.toString())
        }
        
        binding.callDateInput.setOnClickListener { showDatePicker() }
        
        binding.descriptionInput.doAfterTextChanged { text ->
            viewModel.updateDescription(text.toString())
        }
        
        binding.callerInput.doAfterTextChanged { text ->
            viewModel.updateCaller(text.toString())
        }
        
        binding.callReasonInput.doAfterTextChanged { text ->
            viewModel.updateCallReason(text.toString())
        }

        binding.timeTrackingSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateHasTimeTracking(isChecked)
        }

        binding.photosSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateHasPhotos(isChecked)
        }
    }
    
    private fun showDatePicker() {
        val currentDate = viewModel.callDate.value ?: LocalDateTime.now()
        
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                val newDate = LocalDateTime.of(year, month + 1, day,
                    currentDate.hour, currentDate.minute)
                viewModel.updateCallDate(newDate)
            },
            currentDate.year,
            currentDate.monthValue - 1,
            currentDate.dayOfMonth
        ).show()
    }
    
    private fun observeViewModel() {
        viewModel.siteName.observe(viewLifecycleOwner) { name ->
            if (binding.siteInput.text.toString() != name) {
                binding.siteInput.setText(name)
            }
        }
        
        viewModel.callDate.observe(viewLifecycleOwner) { date ->
            binding.callDateInput.setText(date.format(dateFormatter))
        }
        
        viewModel.description.observe(viewLifecycleOwner) { description ->
            if (binding.descriptionInput.text.toString() != description) {
                binding.descriptionInput.setText(description)
            }
        }
        
        viewModel.caller.observe(viewLifecycleOwner) { caller ->
            if (binding.callerInput.text.toString() != caller) {
                binding.callerInput.setText(caller)
            }
        }
        
        viewModel.callReason.observe(viewLifecycleOwner) { reason ->
            if (binding.callReasonInput.text.toString() != reason) {
                binding.callReasonInput.setText(reason)
            }
        }

        viewModel.hasTimeTracking.observe(viewLifecycleOwner) { hasTracking ->
            binding.timeTrackingSwitch.isChecked = hasTracking
        }

        viewModel.hasPhotos.observe(viewLifecycleOwner) { hasPhotos ->
            binding.photosSwitch.isChecked = hasPhotos
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    companion object {
        fun newInstance(reportId: Long) = ReportInfoFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_REPORT_ID, reportId)
            }
        }
        
        private const val ARG_REPORT_ID = "report_id"
    }
}