package com.example.outiz.ui.reports.tabs

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import com.example.outiz.databinding.FragmentReportInfoBinding
import com.example.outiz.ui.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ReportInfoFragment : Fragment() {
    private var _binding: FragmentReportInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by viewModels({ requireParentFragment() })
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

        setupSiteInput()
        setupDatePicker()
        setupDescriptionInput()
        setupSwitches()
        observeViewModel()
    }

    private fun setupSiteInput() {
        (binding.siteInput as? AutoCompleteTextView)?.let { autoComplete ->
            val sites = listOf("Site A", "Site B", "Site C") // TODO: Get from database
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, sites)
            autoComplete.setAdapter(adapter)
            
            autoComplete.doAfterTextChanged { text ->
                viewModel.updateSiteName(text.toString())
            }
        }
    }

    private fun setupDatePicker() {
        binding.dateInput.setOnClickListener {
            val currentDate = viewModel.date.value ?: LocalDateTime.now()
            
            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    val newDate = LocalDateTime.of(year, month + 1, day,
                        currentDate.hour, currentDate.minute)
                    viewModel.updateDate(newDate)
                },
                currentDate.year,
                currentDate.monthValue - 1,
                currentDate.dayOfMonth
            ).show()
        }
    }

    private fun setupDescriptionInput() {
        binding.descriptionInput.doAfterTextChanged { text ->
            viewModel.updateDescription(text.toString())
        }
    }

    private fun setupSwitches() {
        binding.timeTrackingSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateHasTimeTracking(isChecked)
        }

        binding.photosSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateHasPhotos(isChecked)
        }
    }

    private fun observeViewModel() {
        viewModel.siteName.observe(viewLifecycleOwner) { name ->
            if (binding.siteInput.text.toString() != name) {
                binding.siteInput.setText(name)
            }
        }

        viewModel.date.distinctUntilChanged().observe(viewLifecycleOwner) { date ->
            binding.dateInput.setText(date.format(dateFormatter))
        }

        viewModel.description.observe(viewLifecycleOwner) { description ->
            if (binding.descriptionInput.text.toString() != description) {
                binding.descriptionInput.setText(description)
            }
        }

        viewModel.hasTimeTracking.observe(viewLifecycleOwner) { hasTimeTracking ->
            binding.timeTrackingSwitch.isChecked = hasTimeTracking
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
        fun newInstance() = ReportInfoFragment()
    }
}