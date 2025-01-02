package com.example.outiz.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.outiz.databinding.FragmentEditReportBinding
import com.example.outiz.ui.adapter.ReportPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditReportFragment : Fragment() {
    private var _binding: FragmentEditReportBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ReportViewModel by viewModels()
    
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
        
        setupPager()
        setupListeners()
        
        // Load report if editing
        arguments?.getLong(ARG_REPORT_ID, -1L)?.let { reportId ->
            if (reportId != -1L) {
                viewModel.loadReport(reportId)
            }
        }
    }
    
    private fun setupPager() {
        val pagerAdapter = ReportPagerAdapter(
            requireActivity(),
            reportId = arguments?.getLong(ARG_REPORT_ID, -1L) ?: -1L,
            hasTimeTracking = true,
            hasPhotos = true
        )
        
        binding.viewPager.adapter = pagerAdapter
        
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Informations"
                1 -> "Temps"
                2 -> "Photos"
                else -> ""
            }
        }.attach()
    }
    
    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            saveReport()
        }
    }
    
    private fun saveReport() {
        viewModel.saveReport { success ->
            if (success) {
                findNavController().navigateUp()
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    companion object {
        const val ARG_REPORT_ID = "arg_report_id"
    }
}