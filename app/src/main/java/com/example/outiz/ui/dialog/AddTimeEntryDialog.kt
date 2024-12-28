package com.example.outiz.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.outiz.databinding.DialogAddTimeEntryBinding
import com.example.outiz.ui.reports.EditReportViewModel
import java.time.LocalDateTime

class AddTimeEntryDialog : DialogFragment() {
    private lateinit var binding: DialogAddTimeEntryBinding
    private val viewModel: EditReportViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogAddTimeEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            val reportId = arguments?.getLong(ARG_REPORT_ID) ?: return@setOnClickListener
            val startTime = LocalDateTime.parse(binding.etStartTime.text.toString())
            val duration = binding.etDuration.text.toString().toLong()
            val description = binding.etDescription.text.toString()

            // TODO: Add actual save logic
        }
    }

    companion object {
        private const val ARG_REPORT_ID = "report_id"

        fun newInstance(reportId: Long): AddTimeEntryDialog {
            val fragment = AddTimeEntryDialog()
            val args = Bundle()
            args.putLong(ARG_REPORT_ID, reportId)
            fragment.arguments = args
            return fragment
        }
    }
}