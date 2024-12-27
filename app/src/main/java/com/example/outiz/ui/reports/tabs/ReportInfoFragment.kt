package com.example.outiz.ui.reports.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.outiz.databinding.FragmentReportInfoBinding
import com.example.outiz.models.Site
import com.example.outiz.ui.reports.EditReportViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class ReportInfoData(
    val siteId: String,
    val callDate: Date,
    val callReason: String,
    val caller: String,
    val isTimeTrackingEnabled: Boolean,
    val isPhotosEnabled: Boolean
)

class ReportInfoFragment : Fragment() {

    private var _binding: FragmentReportInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditReportViewModel by viewModels({ requireParentFragment() })

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    private var selectedDate: Date = Date()
    private var selectedSite: Site? = null

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
        viewModel.sites.observe(viewLifecycleOwner) { sites ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                sites.map { it.name }
            )
            binding.siteInput.setAdapter(adapter)

            // Mettre à jour la référence au site sélectionné
            selectedSite?.let { selected ->
                selectedSite = sites.find { it.id == selected.id }
            }
        }

        viewModel.report.observe(viewLifecycleOwner) { report ->
            report?.let {
                selectedSite = it.site
                binding.siteInput.setText(it.site.name, false)
                selectedDate = it.report.callDate
                binding.callDateInput.setText(dateFormat.format(selectedDate))
                binding.callReasonInput.setText(it.report.callReason)
                binding.callerInput.setText(it.report.caller)
                binding.timeTrackingSwitch.isChecked = it.report.isTimeTrackingEnabled
                binding.photosSwitch.isChecked = it.report.isPhotosEnabled
            }
        }
    }

    private fun setupListeners() {
        binding.siteInput.setOnItemClickListener { _, _, position, _ ->
            val siteName = binding.siteInput.text.toString()
            selectedSite = viewModel.sites.value?.find { it.name == siteName }
        }

        binding.callDateInput.setOnClickListener {
            showDateTimePicker()
        }
    }

    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance().apply {
            time = selectedDate
        }

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(calendar.timeInMillis)
            .build()

        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .build()

        datePicker.addOnPositiveButtonClickListener { selectedDateInMillis ->
            timePicker.show(childFragmentManager, "TimePicker")
            timePicker.addOnPositiveButtonClickListener {
                calendar.timeInMillis = selectedDateInMillis
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
                calendar.set(Calendar.MINUTE, timePicker.minute)
                selectedDate = calendar.time
                binding.callDateInput.setText(dateFormat.format(selectedDate))
            }
        }

        datePicker.show(childFragmentManager, "DatePicker")
    }

    fun validateAndCollectData(): ReportInfoData? {
        val site = selectedSite ?: return null
        val callReason = binding.callReasonInput.text.toString()
        val caller = binding.callerInput.text.toString()

        if (callReason.isBlank() || caller.isBlank()) {
            return null
        }

        return ReportInfoData(
            siteId = site.id,
            callDate = selectedDate,
            callReason = callReason,
            caller = caller,
            isTimeTrackingEnabled = binding.timeTrackingSwitch.isChecked,
            isPhotosEnabled = binding.photosSwitch.isChecked
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}