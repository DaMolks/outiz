package com.example.outiz.ui.reports.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outiz.databinding.FragmentReportTimeBinding
import com.example.outiz.ui.reports.ReportViewModel

class ReportTimeFragment : Fragment() {
    private var _binding: FragmentReportTimeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReportViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewTime.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TimeEntryAdapter()
        }
    }

    private fun setupListeners() {
        binding.addTimeButton.setOnClickListener {
            // TODO: Implement time entry dialog
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}